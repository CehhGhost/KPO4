package com.example.demo.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.BindException;

/**
 * Класс-RESTконтроллер, описывающий все запросы аутентификации
 * Доступен по ссылке /api/v1/auth
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    // Сервис аутентификации, реализующий все запросы
    private final AuthenticationService service;

    /**
     * Post-метод, добавляющий пользователя в систему
     *
     * @param request Тело запроса регистрации
     * @return Ответ на запрос
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    /**
     * Post-метод, авторизирующий пользователя в систему
     *
     * @param request Тело запроса аутентификации
     * @return Ответ на запрос
     */
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationRequest request) throws BindException {
        return ResponseEntity.ok(service.authenticate(request));
    }
}
