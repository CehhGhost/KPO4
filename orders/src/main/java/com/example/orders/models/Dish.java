package com.example.orders.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * Класс блюда
 * Аннотация Entity сохраняет класс, как часть датабазы в таблице "_dishes"
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_dishes")
public class Dish {
    // id блюда
    @Id
    @GeneratedValue
    private Integer id;
    // Название блюда, не должно быть пустым
    @Column(nullable = false)
    private String name;
    // Описание блюда
    private String description;
    // Цена блюда
    private BigDecimal price;
    // Число блюд, доступных для заказа
    private Integer quantity;
}
