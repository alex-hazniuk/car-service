package org.example.management.actions.orderActions;

import org.example.management.actions.Action;

public class ListOrders extends Action {
    @Override
    public void execute() {
        System.out.println(genericInit.getOrderService().getAll());
    }
}
