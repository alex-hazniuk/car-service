package org.example.management.actions.orderActions;

import org.example.exception.InappropriateStatusException;
import org.example.exception.InvalidIdException;
import org.example.management.actions.Action;

public class AssignRepairer extends Action {
    @Override
    public void execute() {
        System.out.println("For assign a repairer to the order please enter order id: ");
        long id = scanner.nextLong();

        System.out.println("Please enter repairer id: ");
        int repairerId = scanner.nextInt();

        try {
            System.out.println(genericInit.getOrderService().assignRepairer(id, repairerId));
            System.out.println("The repairer was successfully added to the order.");
        } catch (InvalidIdException | InappropriateStatusException e) {
            System.out.println(e.getMessage());
        }
    }
}
