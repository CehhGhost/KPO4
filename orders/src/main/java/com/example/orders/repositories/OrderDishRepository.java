package com.example.orders.repositories;

import com.example.orders.models.OrderDish;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Класс репозитория блюда в заказе
 * Выполняет свзяь с бд
 */
@Repository
public interface OrderDishRepository extends CrudRepository<OrderDish, Integer> {
}
