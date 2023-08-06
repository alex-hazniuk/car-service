package org.example.management.actions.repairerActions;

import org.example.exception.InvalidNameException;
import org.example.management.actions.Action;
import org.example.management.actions.initServices.RepairerServiceInit;

public class RemoveRepairer extends RepairerServiceInit implements Action {
    @Override
    public void execute() {
        System.out.println("To remove a repairer please enter his name: ");

        String name = scanner.next();

        boolean removed = false;
        try {
            removed = repairerService.remove(name);
        } catch (InvalidNameException e) {
            System.out.println(e.getMessage());
        }

        if (removed) {
            System.out.println("The repairer was successfully deleted.");
        } else {
            System.out.println("Something went wrong... The repairer wasn't deleted.");
        }
    }
}
