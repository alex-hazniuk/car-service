package org.example.management.actions.orderActions;

import org.example.management.actions.Action;
import org.example.management.actions.initServices.OrderServiceInit;
import org.example.model.Order;

import java.time.LocalDateTime;

public class AddOrder extends OrderServiceInit implements Action {
    @Override
    public void execute() {
        Order order = new Order();
        order.setCreatedAt(LocalDateTime.now());

        System.out.println(orderService.create(order));
        System.out.println("Order was successfully created.");
    }
}
