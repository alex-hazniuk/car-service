package org.example.management.actions.repairerActions;

import org.example.management.actions.Action;
import org.example.management.actions.initServices.RepairerServiceInit;

public class FindRepairerById extends RepairerServiceInit implements Action {
    @Override
    public void execute() {
        System.out.println("To view the repairer information please enter his id: ");

        int id = scanner.nextInt();

        System.out.println(repairerService.findById(id));
    }
}
