package com.example.orders.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Класс блюда в заказе
 * Аннотация Entity сохраняет класс, как часть датабазы в таблице "_order_dishes"
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_order_dishes")
public class OrderDish {
    // id блюда в заказе
    @Id
    @GeneratedValue
    private Integer id;
    // Заказ, в котором блюдо
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
    // id блюда
    private Integer dish;
    // Количество блюда в заказе
    private Integer quantity;
    // Цена блюда в заказе
    private BigDecimal price;
}
