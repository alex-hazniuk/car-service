package org.example.management.actions.orderActions;

import org.example.exception.InvalidIdException;
import org.example.management.actions.Action;
import org.example.management.actions.initServices.OrderServiceInit;

public class CompleteOrder extends OrderServiceInit implements Action {
    @Override
    public void execute() {
        System.out.println("To complete the order please enter its id: ");

        long id = scanner.nextLong();

        try {
            System.out.println(orderService.completeOrder(id));
        } catch (InvalidIdException e) {
            System.out.println(e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("No repairers to change their status!!!");
        }
        System.out.println("Order completed.");
    }
}
