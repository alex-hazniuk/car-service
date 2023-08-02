package org.example.management.actions.orderActions;

import org.example.management.actions.Action;
import org.example.management.actions.initServices.OrderServiceInit;

public class CompleteOrder extends OrderServiceInit implements Action {
    @Override
    public void execute() {
        System.out.println("To complete the order please enter its id: ");

        long id = scanner.nextLong();

        System.out.println(orderService.completeOrder(id));
        System.out.println("Order completed.");
    }
}
