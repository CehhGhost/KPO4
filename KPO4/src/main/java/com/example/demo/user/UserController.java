package com.example.demo.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.BindException;

/**
 * Класс-RESTконтроллер, описывающий все запросы пользователю
 * Доступен по ссылке /api/v1/user
 */
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    // Сервис, выполняющий запросы контроллера
    private final UserService service;

    /**
     * get-метод, возвращающий пользователя по токену
     * @param token токен
     * @throws BindException
     * @return Ответ на запрос
     */
    @GetMapping("/get")
    public ResponseEntity<User> GetByToken(@RequestBody TokenRequest token) throws BindException {
        return ResponseEntity.ok(service.getByToken(token));
    }
}
