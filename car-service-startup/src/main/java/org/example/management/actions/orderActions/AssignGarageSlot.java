package org.example.management.actions.orderActions;

import org.example.exception.InvalidIdException;
import org.example.exception.InappropriateStatusException;
import org.example.management.actions.Action;

public class AssignGarageSlot extends Action {
    @Override
    public void execute() {
        System.out.println("For assign a garage slot to the order please enter order id: ");
        long id = scanner.nextLong();

        System.out.println("Please enter garage slot id: ");
        int garageId = scanner.nextInt();

        try {
            System.out.println(genericInit.getOrderService().assignGarageSlot(id, garageId));
            System.out.println("The garage slot was successfully added to the order.");
        } catch (InvalidIdException | InappropriateStatusException e) {
            System.out.println(e.getMessage());
        }
    }
}
