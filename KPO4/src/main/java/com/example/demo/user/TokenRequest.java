package com.example.demo.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс, описывающий запрос токена
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenRequest {
    // Токен
    private String token;
}
