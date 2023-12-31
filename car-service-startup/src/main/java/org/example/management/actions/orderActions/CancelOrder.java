package org.example.management.actions.orderActions;

import org.example.exception.InvalidIdException;
import org.example.management.actions.Action;

public class CancelOrder extends Action {
    @Override
    public void execute() {
        System.out.println("To cancel the order please enter its id: ");

        long id = scanner.nextLong();

        try {
            System.out.println(genericInit.getOrderService().cancelOrder(id));
            System.out.println("Order canceled.");
        } catch (InvalidIdException e) {
            System.out.println(e.getMessage());
        }
    }
}
