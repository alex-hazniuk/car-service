package org.example.management.actions.repairerActions;

import org.example.exception.InvalidIdException;
import org.example.management.actions.Action;
import org.example.management.actions.initServices.RepairerServiceInit;
import org.example.model.Repairer;

public class FindRepairerById extends RepairerServiceInit implements Action {
    @Override
    public void execute() {
        System.out.println("To view the repairer information please enter his id: ");

        int id = scanner.nextInt();
        /*Repairer repairer = repairerService.findById(id);
        if (repairer.getId() != 0) {
            System.out.println(repairer);
        }*/
        try {
            System.out.println(repairerService.findById(id));
        } catch (InvalidIdException e) {
            System.out.println(e.getMessage());
        }
    }
}
