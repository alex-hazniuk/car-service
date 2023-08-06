package org.example.management.actions.orderActions;

import org.example.service.SortType;
import org.example.management.actions.Action;
import org.example.management.actions.initServices.OrderServiceInit;

public class ListOrders extends OrderServiceInit implements Action {
    @Override
    public void execute() {
        System.out.println("Order by: ");
        System.out.println("""
                1. Price
                2. Creation date
                3. Completion date
                4. Status""");

        int order = scanner.nextInt();
        SortType sortType = SortType.values()[order - 1];

        System.out.println(orderService.listOrders(sortType));
    }
}
