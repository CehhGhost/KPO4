package com.example.orders.repositories;

import com.example.orders.models.OrderDish;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDishRepository extends CrudRepository<OrderDish, Integer> {
}
