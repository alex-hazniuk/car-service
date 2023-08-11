package org.example.management.actions.orderActions;

import org.example.service.SortType;
import org.example.management.actions.Action;

public class ListOrders extends Action {
    @Override
    public void execute() {
        System.out.println("Order by: ");
        System.out.println("""
                1. Price
                2. Creation date
                3. Completion date
                4. Status""");

        int order = scanner.nextInt();
        SortType sortType;
        switch (order) {
            case 2 -> sortType = SortType.CREATED_AT;
            case 3 -> sortType = SortType.COMPLETED_AT;
            case 4 -> sortType = SortType.STATUS;
            default -> sortType = SortType.PRICE;
        }

        System.out.println(genericInit.getOrderService().listOrders(sortType));
    }
}
