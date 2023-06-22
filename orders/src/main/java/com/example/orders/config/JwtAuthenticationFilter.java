package com.example.orders.config;

import com.example.orders.config.JwtService;
import com.example.orders.services.ConnectionService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Класс, отвечающий за фильтрацию запросов и валидацию JWT токенов
 * Наследует класс OncePerRequestFilter, благодаря чему вызывается лишь раз за запрос
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    // Сервис JWT
    private final JwtService jwtService;
    // Вспомогательный сервис для пользователя
    private final ConnectionService connectionService;

    /**
     * Перегружает метод для фильтрации
     * Достает из запроса токен, чтобы достать email пользователя, проверяет его на сущестовавание и валидирует токен с помощью UserDetailsService
     *
     * @param request     Запрос
     * @param response    Ответ
     * @param filterChain Цепочка фильтрации
     * @throws ServletException
     */
    @SneakyThrows
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            throw new ServletException("You have to be authenticated!");
        }
        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt);
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            String userDetails = connectionService.getUserEmailByToken(jwt);
            if (!jwtService.isTokenValid(jwt, userDetails)) {
                throw new ServletException("You have to be authenticated!");
            }
            filterChain.doFilter(request, response);
        }
    }
}
