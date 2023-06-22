package com.example.orders.repositories;

import com.example.orders.models.Dish;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Класс репозитория блюда
 * Выполняет свзяь с бд
 */
@Repository
public interface DishRepository extends CrudRepository<Dish, Integer> {
    /**
     * Метод, позволяющий найти блюдо, по его названию
     *
     * @return Возможно блюдо, если такое найдется
     */
    Optional<Dish> findByName(String name);
}
