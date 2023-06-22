package com.example.orders.requests;

import com.example.orders.models.OrderDish;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Метод-запрос для создания заказа
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GenerateOrderRequest {
    // id пользователя, создавшего заказ
    private Integer user_id;
    // Статут заказа
    private String status;
    // Особые требования к заказу
    private String special_requests;
    // Время создания заказа
    private Timestamp created_at;
    // Время последнего обновления заказа
    private Timestamp updated_at;
    // Список блюд в заказе
    private List<OrderDish> orderDishes = new ArrayList<>();
}
