package org.example.repository;

import org.example.model.Order;

import java.util.List;

public interface OrderRepository {

    Order save(Order order);

    //Optional<Order> findById(Long id);
    Order findById(Long id);

    List<Order> findAll();

    Order update(Long id, Order order);
}