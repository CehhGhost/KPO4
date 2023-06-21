package com.example.orders.requests;

import com.example.orders.models.OrderDish;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GenerateOrderRequest {
    private Integer user_id;
    private String status;
    private String special_requests;
    private Timestamp created_at;
    private Timestamp updated_at;
    private List<OrderDish> orderDishes = new ArrayList<>();
}
