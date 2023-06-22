package com.example.orders.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * Класс заказа
 * Аннотация Entity сохраняет класс, как часть датабазы в таблице "_orders"
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_orders")
public class Order {
    // id заказа
    @Id
    @GeneratedValue
    private Integer id;
    // id пользователя
    private Integer user_id;
    // Статус заказа
    private String status;
    // Особые указания к заказу
    private String special_requests;
    // Создан в
    private Timestamp created_at;
    // Обновлен в
    private Timestamp updated_at;
}
