package com.example.orders.repositories;

import com.example.orders.models.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {
    Iterable<Order> findAllByStatus(String status);
    Optional<Order> findByStatus(String status);
}
