package com.example.orders.repositories;

import com.example.orders.models.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Класс репозитория заказа
 * Выполняет свзяь с бд
 */
@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {
    /**
     * Метод, позволяющий найти все заказы, по их статусу
     *
     * @return Список блюд
     */
    Iterable<Order> findAllByStatus(String status);
    /**
     * Метод, позволяющий найти заказ, по его статусу
     *
     * @return Возможно заказ, если такой найдется
     */
    Optional<Order> findByStatus(String status);
}
