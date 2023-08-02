package org.example.management.actions.orderActions;

import org.example.management.actions.Action;
import org.example.management.actions.initServices.OrderServiceInit;

public class CancelOrder extends OrderServiceInit implements Action {
    @Override
    public void execute() {
        System.out.println("To cancel the order please enter its id: ");

        long id = scanner.nextLong();

        System.out.println(orderService.cancelOrder(id));
        System.out.println("Order canceled.");
    }
}
