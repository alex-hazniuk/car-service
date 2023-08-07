package org.example.management.actions.orderActions;

import org.example.exception.InappropriateStatusException;
import org.example.exception.InvalidIdException;
import org.example.management.actions.Action;
import org.example.management.actions.initServices.OrderServiceInit;

public class AssignGarageSlot extends OrderServiceInit implements Action {
    @Override
    public void execute() {
        System.out.println("For assign a garage slot to the order please enter order id: ");
        long id = scanner.nextLong();

        System.out.println("Please enter garage slot id: ");
        int garageId = scanner.nextInt();

        try {
            System.out.println(orderService.assignGarageSlot(id, garageId));
        } catch (InvalidIdException | InappropriateStatusException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("The garage slot was successfully added to the order.");
    }
}
