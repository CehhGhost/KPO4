package com.example.orders.controllers;

import com.example.orders.models.Order;
import com.example.orders.requests.GenerateOrderRequest;
import com.example.orders.responses.GetOrderResponse;
import com.example.orders.services.ConnectionService;
import com.example.orders.services.OrderService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

/***
 * Контроллер для работы с заказами.
 */
@Slf4j
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    // Сервис заказов
    private final OrderService orderService;
    // Сервис подключения (обеспечивает создание запросов к микросервису авторизации)
    private final ConnectionService connectionService;

    /***
     * API endpoint для создания нового заказа
     * @param request данные о заказе
     * @param token токен авторизации пользователя
     * @throws URISyntaxException - ошибка при отправке запроса другому сервису
     * @return объект заказа
     */
    @PostMapping
    public ResponseEntity<Order> generateOrder(@RequestBody GenerateOrderRequest request, HttpServletRequest token) throws URISyntaxException {
        request.setUser_id(connectionService.getUserIdByToken(token.getHeader("Authorization").substring(7)));
        return ResponseEntity.ok(orderService.GenerateOrder(request));
    }
    /***
     * API endpoint для получения заказа по id
     * @param id идентификатор заказа
     * @return объект заказа
     */
    @GetMapping("/{id}")
    public ResponseEntity<GetOrderResponse> getOrderById(@PathVariable Integer id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }
    /***
     * API endpoint для обновления статуса заказа
     * @return сообщение об успешном выполнении обновления статуса
     */
    @PutMapping
    public ResponseEntity<String> updateOrderStatus() {
        return ResponseEntity.ok(orderService.randomlyUpdateOrderStatus());
    }
    /***
     * Обработчик ошибок, возникающих при работе с заказами
     * @param e исключение, возникшее при работе с заказами
     * @return сообщение об ошибке
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body("Something went wrong: " + e.getMessage());
    }
    /***
     * Обработчик ошибок, возникающих при работе с сервлетом
     * @param e исключение, возникшее при работе с сервлетом
     * @return сообщение об ошибке
     */
    @ExceptionHandler(ServletException.class)
    public ResponseEntity<String> handleServletException(ServletException e) {
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body("Something went wrong: " + e.getMessage());
    }
    /***
     * Обработчик ошибок, возникающих при работе с истекшим JWT токеном
     * @param e исключение, возникшее при работе с JWT токеном
     * @return сообщение об ошибке
     */
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<String> handleExpiredJwtException(ExpiredJwtException e) {
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body("Something went wrong: Your token is expired");
    }
}
