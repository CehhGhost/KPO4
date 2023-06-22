package com.example.orders.controllers;

import com.example.orders.models.Dish;
import com.example.orders.requests.CreateOrUpdateDishRequest;
import com.example.orders.services.DishService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.ServletException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Класс-RESTконтроллер, описывающий все запросы к блюдам
 * Доступен по ссылке /dishes
 */
@Slf4j
@RestController
@RequestMapping("/dishes")
@RequiredArgsConstructor
public class DishController {
    // Сервис блюд, реализующий все запросы
    private final DishService dishService;

    /***
     * Обработчик GET-запроса на получение всех блюд (по сути - меню).
     * @return список всех имеющихся блюд.
     ***/
    @GetMapping
    public ResponseEntity<List<Dish>> getAllDishes() {
        return ResponseEntity.ok(dishService.getAllDishes());
    }
    /***
     * Обработчик POST-запроса на создание нового блюда.
     * @param request - данные для создания блюда.
     * @return ответ об успешном создании блюда.
     ***/
    @PostMapping
    public ResponseEntity<String> createDish(@RequestBody CreateOrUpdateDishRequest request) {
        dishService.createDishRequest(request);
        return ResponseEntity.ok("Successfully created dish");
    }
    /***
     * Обработчик GET-запроса на получение блюда по ID.
     * @param id - ID блюда.
     * @return блюдо с указанным ID.
     ***/
    @GetMapping("/{id}")
    public ResponseEntity<Dish> getDish(@PathVariable Integer id) {
        return ResponseEntity.ok(dishService.getDishById(id));
    }
    /***
     * Обработчик PUT-запроса на обновление блюда по ID.
     * @param id - ID блюда.
     * @param request - данные для обновления блюда.
     * @return ответ об успешном обновлении блюда.
     ***/
    @PutMapping("/{id}")
    public ResponseEntity<String> updateDish(@PathVariable Integer id, @RequestBody CreateOrUpdateDishRequest request) {
        dishService.updateDishById(id, request);
        return ResponseEntity.ok("Successfully updated dish");
    }
    /***
     * Обработчик DELETE-запроса на удаление блюда по ID.
     * @param id - ID блюда.
     * @return ответ об успешном удалении блюда.
     ***/
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDish(@PathVariable Integer id) {
        dishService.deleteDishById(id);
        return ResponseEntity.ok("Successfully deleted dish");
    }
    /***
     * Обработчик ошибок IllegalArgumentException.
     ***/
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handle(IllegalArgumentException e) {
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body("Something went wrong: " + e.getMessage());
    }
    /***
     * Обработчик ошибок ServletException.
     ***/
    @ExceptionHandler(ServletException.class)
    public ResponseEntity<String> handleServletException(ServletException e) {
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body("Something went wrong: " + e.getMessage());
    }
    /***
     * Обработчик ошибок ExpiredJwtException.
     ***/
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<String> handleExpiredJwtException(ExpiredJwtException e) {
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body("Something went wrong: Your token is expired");
    }
}
