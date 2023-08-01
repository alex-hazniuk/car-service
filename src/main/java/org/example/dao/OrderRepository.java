package org.example.dao;

import org.example.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    Order save(Order order);
    Optional<Order> findById(Long id);
    List<Order> findAll();
    Order update(Long id, Order order);
}