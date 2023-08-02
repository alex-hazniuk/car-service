package org.example.management.actions.orderActions;

import org.example.management.actions.Action;
import org.example.management.actions.initServices.OrderServiceInit;

public class FindOrderById extends OrderServiceInit implements Action {
    @Override
    public void execute() {
        System.out.println("To view the order information please enter its id: ");

        long id = scanner.nextLong();

        System.out.println(orderService.findById(id));
    }
}
