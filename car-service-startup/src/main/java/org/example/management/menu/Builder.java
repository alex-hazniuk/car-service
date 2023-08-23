package org.example.management.menu;

import org.example.management.actions.EmptyAction;
import org.example.management.actions.Exit;
import org.example.management.actions.garageSlotActions.*;
import org.example.management.actions.orderActions.*;
import org.example.management.actions.repairerActions.*;

public class Builder {

    private Menu rootMenu;

    public Menu getRootMenu() {
        return rootMenu;
    }

    public void buildMenu() {
        rootMenu = new Menu("Main menu");
        rootMenu.addMenuItem(new MenuItem(1, "Repairer menu", new EmptyAction(), repairerMenu()));
        rootMenu.addMenuItem(new MenuItem(2, "Garage slot menu", new EmptyAction(), garageSlotMenu()));
        rootMenu.addMenuItem(new MenuItem(3, "Order menu", new EmptyAction(), orderMenu()));
        rootMenu.addMenuItem(new MenuItem(4, "Exit", new Exit(), null));
    }

    private Menu repairerMenu() {
        Menu repairerMenu = new Menu("Repairer menu");
        repairerMenu.addMenuItem(new MenuItem(1, "Add repairer", new AddRepairer(), repairerMenu));
        repairerMenu.addMenuItem(new MenuItem(2, "Remove repairer", new RemoveRepairer(), repairerMenu));
        repairerMenu.addMenuItem(new MenuItem(3, "Get repairer information",
                new FindRepairerById(), repairerMenu));
        repairerMenu.addMenuItem(new MenuItem(4, "View list repairer", new EmptyAction(), listRepairerMenu()));
        repairerMenu.addMenuItem(new MenuItem(5, "Change repairer status", new ChangeStatus(), repairerMenu));
        repairerMenu.addMenuItem(new MenuItem(6, "Back to previous menu", new EmptyAction(), rootMenu));
        repairerMenu.addMenuItem(new MenuItem(7, "Exit", new Exit(), null));

        return repairerMenu;
    }

    private Menu listRepairerMenu() {
        Menu listRepairerMenu = new Menu("Repairers list");
        listRepairerMenu.addMenuItem(new MenuItem(1, "Repairers list", new ListRepairers(), rootMenu));
        listRepairerMenu.addMenuItem(new MenuItem(2, "Repairers list ordered by name",
                new ListRepairersByName(), rootMenu));
        listRepairerMenu.addMenuItem(new MenuItem(3, "Repairers list ordered by status",
                new ListRepairersByStatus(), rootMenu));
        listRepairerMenu.addMenuItem(new MenuItem(4, "Back to previous menu", new EmptyAction(), rootMenu));
        listRepairerMenu.addMenuItem(new MenuItem(5, "Exit", new Exit(), null));

        return listRepairerMenu;
    }

    private Menu garageSlotMenu() {
        Menu garageSlotMenu = new Menu("Garage slots menu");
        garageSlotMenu.addMenuItem(new MenuItem(1, "Add garage slot", new AddGarage(), garageSlotMenu));
        garageSlotMenu.addMenuItem(new MenuItem(2, "Change garage slot status",
                new ChangeGarageStatus(), garageSlotMenu));
        garageSlotMenu.addMenuItem(new MenuItem(3, "View garage slot information",
                new FindGarageById(), garageSlotMenu));
        garageSlotMenu.addMenuItem(new MenuItem(4, "View all garage slots", new EmptyAction(), listGarageSlotsMenu()));
        garageSlotMenu.addMenuItem(new MenuItem(5, "Remove garage slot", new RemoveGarage(), garageSlotMenu));
        garageSlotMenu.addMenuItem(new MenuItem(6, "Back to previous menu", new EmptyAction(), rootMenu));
        garageSlotMenu.addMenuItem(new MenuItem(7, "Exit", new Exit(), null));

        return garageSlotMenu;
    }

    private Menu listGarageSlotsMenu() {
        Menu listGarageSlotsMenu = new Menu("Garage slots list");
        listGarageSlotsMenu.addMenuItem(new MenuItem(1, "Garage slots list",
                new ListGarageSlots(), rootMenu));
        listGarageSlotsMenu.addMenuItem(new MenuItem(2, "Garage slots list ordered by status",
                new ListGarageSlotsByStatus(), rootMenu));
        listGarageSlotsMenu.addMenuItem(new MenuItem(3, "Back to previous menu", new EmptyAction(), rootMenu));
        listGarageSlotsMenu.addMenuItem(new MenuItem(4, "Exit", new Exit(), null));

        return listGarageSlotsMenu;
    }

    private Menu orderMenu() {
        Menu orderMenu = new Menu("Order menu");
        orderMenu.addMenuItem(new MenuItem(1, "Add order", new AddOrder(), orderMenu));
        orderMenu.addMenuItem(new MenuItem(2, "Assign garage slot", new AssignGarageSlot(), orderMenu));
        orderMenu.addMenuItem(new MenuItem(3, "Assign repairer", new AssignRepairer(), orderMenu));
        orderMenu.addMenuItem(new MenuItem(4, "View order information", new FindOrderById(), orderMenu));
        orderMenu.addMenuItem(new MenuItem(5, "Complete order", new CompleteOrder(), orderMenu));
        orderMenu.addMenuItem(new MenuItem(6, "View orders list", new ListOrders(), orderMenu));
        orderMenu.addMenuItem(new MenuItem(7, "Cancel order", new CancelOrder(), orderMenu));
        orderMenu.addMenuItem(new MenuItem(8, "Back to previous menu", new EmptyAction(), rootMenu));
        orderMenu.addMenuItem(new MenuItem(9, "Exit", new Exit(), null));

        return orderMenu;
    }
}
