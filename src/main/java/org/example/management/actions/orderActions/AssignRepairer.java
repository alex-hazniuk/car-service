package org.example.management.actions.orderActions;

import org.example.management.actions.Action;
import org.example.management.actions.initServices.OrderServiceInit;

public class AssignRepairer extends OrderServiceInit implements Action {
    @Override
    public void execute() {
        System.out.println("For assign a repairer to the order please enter order id: ");
        long id = scanner.nextLong();

        System.out.println("Please enter repairer id: ");
        int repairerId = scanner.nextInt();

        System.out.println(orderService.assignRepairer(id, repairerId));
        System.out.println("The repairer was successfully added to the order.");
    }
}
