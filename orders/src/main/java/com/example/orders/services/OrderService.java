package com.example.orders.services;

import com.example.orders.enums.Status;
import com.example.orders.models.Order;
import com.example.orders.models.OrderDish;
import com.example.orders.repositories.DishRepository;
import com.example.orders.repositories.OrderDishRepository;
import com.example.orders.repositories.OrderRepository;
import com.example.orders.requests.GenerateOrderRequest;
import com.example.orders.responses.GetOrderResponse;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/***
 * Класс OrderService предназначен для работы с сущностью заказов.
 ***/
@Service
@AllArgsConstructor
public class OrderService {
    // Репозиторий заказов
    private final OrderRepository orderRepository;
    // Репозиторий блюд
    private final DishRepository dishRepository;
    // Репозиторий блюд в заказе
    private final OrderDishRepository orderDishRepository;
    /** Метод для создания ногово заказа
     * @param request - запрос создания или обновления заказа
     * @return Соданный заказ
     */
    @Transactional
    public Order GenerateOrder(GenerateOrderRequest request) {
        Order order = new Order();
        order = orderRepository.save(order);
        for (var item : request.getOrderDishes()) {
            var dish = dishRepository.findById(item.getDish());
            if (dish.isEmpty()) {
                throw new IllegalArgumentException("There is no dish with id like this");
            }
            if (item.getQuantity() <= 0) {
                throw new IllegalArgumentException("Quantity must be more than 0");
            }
            if (item.getQuantity() > dish.get().getQuantity()) {
                throw new IllegalArgumentException("Not enough dishes amount for this order");
            } else {
                dish.get().setQuantity(dish.get().getQuantity() - item.getQuantity());
                dishRepository.save(dish.get());
            }
            item.setOrder(order);
            orderDishRepository.save(item);
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
        order.setUser_id(request.getUser_id());
        order.setStatus(request.getStatus());
        order.setSpecial_requests(request.getSpecial_requests());
        order.setCreated_at(request.getCreated_at());
        order.setUpdated_at(request.getUpdated_at());
        return orderRepository.save(order);
    }
    /** Метод для получения заказа по его id
     * @param id - id заказа
     * @return ответ, согласно формату ответа получения заказа
     */
    public GetOrderResponse getOrderById(Integer id) {
        var order = orderRepository.findById(id);
        if (order.isEmpty()) {
            throw new IllegalArgumentException("Wrong id for order");
        }
        var response = new GetOrderResponse();
        response.setId(order.get().getId());
        response.setStatus(order.get().getStatus());
        response.setCreated_at(order.get().getCreated_at());
        response.setUpdated_at(order.get().getUpdated_at());
        response.setSpecial_requests(order.get().getSpecial_requests());
        response.setUser_id(order.get().getUser_id());
        List<OrderDish> orderDishList = new ArrayList<>();
        for (var item : orderDishRepository.findAll()) {
            if (item.getOrder().getId().equals(id)) {
                orderDishList.add(item);
            }
        }
        response.setOrderDishes(orderDishList);
        return response;
    }
    /** Метод для случайного обновления статуса заказа
     * @return Сообщение о готовности заказа/отсутвие заказов в ожидании
     */
    public String randomlyUpdateOrderStatus() {
        var order = (List<Order>)orderRepository.findAllByStatus(Status.IN_ORDER.getStatus());
        if (!order.isEmpty()) {
            order.get(0).setStatus(Status.COMPLETED.getStatus());
            order.get(0).setUpdated_at(new Timestamp(System.currentTimeMillis()));
            orderRepository.save(order.get(0));
            return "Order with id: " + order.get(0).getId() + " is ready!";
        }
        return "There is no order to make";
    }
}
