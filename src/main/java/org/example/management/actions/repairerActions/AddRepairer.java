package org.example.management.actions.repairerActions;

import org.example.management.actions.Action;
import org.example.management.actions.initServices.RepairerServiceInit;

public class AddRepairer extends RepairerServiceInit implements Action {

    @Override
    public void execute() {
        System.out.println("To add a new repairer please enter a repairer's name: ");

        String name = scanner.next();
        repairerService.save(name);

        System.out.println("The repairer was successfully added.");

    }
}
