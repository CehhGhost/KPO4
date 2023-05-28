package com.example.demo.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс, описывающий запрос регистрации
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    // Имя
    private String firstname;
    // Фамилия
    private String lastname;
    // Почта
    private String email;
    // Пароль
    private String password;
}
