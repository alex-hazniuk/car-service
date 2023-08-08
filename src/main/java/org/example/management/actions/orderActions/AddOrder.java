package org.example.management.actions.orderActions;

import org.example.management.actions.Action;
import org.example.model.Order;

import java.time.LocalDateTime;

public class AddOrder implements Action {
    @Override
    public void execute() {
        Order order = new Order();
        order.setCreatedAt(LocalDateTime.now());

        System.out.println(genericInit.getOrderService().create(order));
        System.out.println("Order was successfully created.");
    }
}
