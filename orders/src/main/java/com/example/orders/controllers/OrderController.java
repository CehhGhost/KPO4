package com.example.orders.controllers;

import com.example.orders.models.Order;
import com.example.orders.requests.GenerateOrderRequest;
import com.example.orders.services.ConnectionService;
import com.example.orders.services.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

@Slf4j
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final ConnectionService connectionService;

    @PostMapping
    public Order generateOrder(@RequestBody GenerateOrderRequest request, HttpServletRequest token) throws URISyntaxException {
        request.setUser_id(connectionService.getUserIdByToken(token.getHeader("Authorization").substring(7)));
        return orderService.GenerateOrder(request);
    }
    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Integer id) {
        return orderService.getOrderById(id);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public String handle(IllegalArgumentException e) {
        log.error(e.getMessage());
        return "Something went wrong: " + e.getMessage();
    }
}
