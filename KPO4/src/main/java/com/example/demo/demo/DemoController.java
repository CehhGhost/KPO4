package com.example.demo.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Класс-RESTконтроллер, описывающий демо версию, чтобы убедиться, что авторизыция работает
 * Доступен по ссылке /api/v1/demo-controller
 */
@RestController
@RequestMapping("/api/v1/demo-controller")
public class DemoController {
    /**
     * get-метод, позволяющий понять, что авторизаця работает
     *
     * @return Ответ на запрос
     */
    @GetMapping
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello from secured endpoint");
    }
}
