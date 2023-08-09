package org.example.management.actions.repairerActions;

import org.example.management.actions.Action;

public class AddRepairer implements Action {

    @Override
    public void execute() {
        System.out.println("To add a new repairer please enter a repairer's name: ");

        String name = scanner.next();
        genericInit.getRepairerService().save(name);

        System.out.println("The repairer was successfully added.");

    }
}
