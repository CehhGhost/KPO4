package com.example.orders.responses;

import com.example.orders.models.OrderDish;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

/**
 * Метод-ответ для получения заказа
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetOrderResponse {
    // id заказа
    private Integer id;
    // id пользователя, создавшего заказ
    private Integer user_id;
    // Статус заказа
    private String status;
    // Особые требования к заказу
    private String special_requests;
    // Время создания заказа
    private Timestamp created_at;
    // Время последнего обновления заказа
    private Timestamp updated_at;
    // Список всех блюд в заказе
    private List<OrderDish> orderDishes;
}
