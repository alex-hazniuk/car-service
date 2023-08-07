package org.example.management.actions.orderActions;

import org.example.exception.InappropriateStatusException;
import org.example.exception.InvalidIdException;
import org.example.management.actions.Action;
import org.example.management.actions.initServices.OrderServiceInit;

public class AssignRepairer extends OrderServiceInit implements Action {
    @Override
    public void execute() {
        System.out.println("For assign a repairer to the order please enter order id: ");
        long id = scanner.nextLong();

        System.out.println("Please enter repairer id: ");
        int repairerId = scanner.nextInt();

        try {
            System.out.println(orderService.assignRepairer(id, repairerId));
        } catch (InvalidIdException | InappropriateStatusException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("The repairer was successfully added to the order.");
    }
}
