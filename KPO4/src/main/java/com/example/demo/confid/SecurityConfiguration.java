package com.example.demo.confid;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * Класс, отвечающий за конфигурацию безопасности
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    // Фильтер аутентификации JWT
    private final JwtAuthenticationFilter jwtAuthFilter;
    // Провайдер аутентификации
    private final AuthenticationProvider authecationProvider;

    /**
     * Метод, устанавливающий собственное звено SecurityFilterChain
     *
     * @param http HTTP защитный запрос
     * @throws Exception
     * @return звено SecurityFilterChain
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requests) -> {
                    requests
                            .requestMatchers("/api/v1/auth/**").permitAll()
                            .requestMatchers("/api/v1/demo-controller").authenticated()
                            .requestMatchers("/api/v1/user/**").authenticated();
                }).authenticationProvider(authecationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
