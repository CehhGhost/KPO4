package com.example.orders.services;

import com.example.orders.enums.Status;
import com.example.orders.models.Order;
import com.example.orders.repositories.DishRepository;
import com.example.orders.repositories.OrderRepository;
import com.example.orders.requests.GenerateOrderRequest;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final DishRepository dishRepository;
    @Transactional
    public Order GenerateOrder(GenerateOrderRequest request) {
        for (var item : request.getOrderDishes()) {
            if (item.getQuantity() > item.getDish().getQuantity()) {
                throw new IllegalArgumentException("Not enough dishes amount for this order");
            }
        }
        if (request.getStatus() == null) {
            request.setStatus(Status.IN_ORDER.getStatus());
        }
        if (request.getCreated_at() == null) {
            if (request.getUpdated_at() != null) {
                request.setCreated_at(request.getCreated_at());
            } else {
                request.setCreated_at(new Timestamp(System.currentTimeMillis()));
            }
        }
        if (request.getUpdated_at() == null) {
            if (request.getCreated_at() != null) {
                request.setUpdated_at(request.getCreated_at());
            } else {
                request.setUpdated_at(new Timestamp(System.currentTimeMillis()));
            }
        } else if (request.getUpdated_at().before(request.getCreated_at())) {
            request.setUpdated_at(request.getCreated_at());
        }
        Order order = new Order();
        order.setUser_id(request.getUser_id());
        order.setStatus(request.getStatus());
        order.setSpecial_requests(request.getSpecial_requests());
        order.setCreated_at(request.getCreated_at());
        order.setUpdated_at(request.getUpdated_at());
        order.setOrderDishes(request.getOrderDishes());
        return orderRepository.save(order);
    }

    public Order getOrderById(Integer id) {
        var order = orderRepository.findById(id);
        if (order.isEmpty()) {
            throw new IllegalArgumentException("Wrong id for order");
        }
        return order.get();
    }
}
