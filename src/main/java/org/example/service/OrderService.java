package org.example.service;

import org.example.model.Order;

import java.util.List;

public interface OrderService {

    Order create(Order order);

    List<Order> listOrders(SortType sortType);

    Order assignGarageSlot(Long id, Integer garageSlotId);

    Order assignRepairer(Long id, Integer repairerId);

    Order findById(Long id);

    Order completeOrder(Long id);

    Order cancelOrder(Long id);
}