package org.example.repository;

import org.example.model.Order;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface OrderRepository {

    Order save(Order order);

    Optional<Order> findById(Long id);

    List<Order> findAll();

    Map<Long, Order> findAllMapFormat();

    Order update(Long id, Order order);
}
