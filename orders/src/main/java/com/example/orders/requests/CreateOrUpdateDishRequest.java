package com.example.orders.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Метод-запрос для создания или обновления блюда
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrUpdateDishRequest {
    // Название блюда
    private String name;
    // Описание блюда
    private String description;
    // Цена блюда
    private BigDecimal price;
    // Число доступное для приготовления блюд
    private Integer quantity;
}
