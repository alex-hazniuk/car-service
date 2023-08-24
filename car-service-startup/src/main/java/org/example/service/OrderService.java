package org.example.service;

import org.example.model.Order;

import java.util.List;

public interface OrderService {

    Order create(Order order);

    List<Order> getAll();

    List<Order> getAllSortedByStatus();

    List<Order> getAllSortedByPrice();

    List<Order> getAllSortedByCreatedDate();

    List<Order> getAllSortedByCompletedDate();

    Order assignGarageSlot(Long id, Integer garageSlotId);

    Order assignRepairer(Long id, Integer repairerId);

    Order findById(Long id);

    Order completeOrder(Long id);

    Order cancelOrder(Long id);
}