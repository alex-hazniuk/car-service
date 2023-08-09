package org.example.management.actions.orderActions;

import org.example.exception.InvalidIdException;
import org.example.management.actions.Action;

public class CompleteOrder implements Action {
    @Override
    public void execute() {
        System.out.println("To complete the order please enter its id: ");

        long id = scanner.nextLong();

        try {
            System.out.println(genericInit.getOrderService().completeOrder(id));
            System.out.println("Order completed.");
        } catch (InvalidIdException e) {
            System.out.println(e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("No repairers to change their status!!!");
        }
    }
}
