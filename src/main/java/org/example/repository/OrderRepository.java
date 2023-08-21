package org.example.repository;

import org.example.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    Order save(Order order);

    Optional<Order> findById(Long id);

    List<Order> findAll();

    List<Order> findAllSortedByStatus();

    List<Order> findAllSortedByPrice();

    List<Order> findAllSortedByCreatedDate();

    List<Order> findAllSortedByCompletedDate();

    Order update(Long id, Order order);
}
