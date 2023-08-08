package org.example.management.actions.repairerActions;

import org.example.exception.InvalidIdException;
import org.example.management.actions.Action;

public class ChangeStatus implements Action {

    @Override
    public void execute() {
        System.out.println("To change repairer status please enter repairers id: \n ID: ");

        int id = scanner.nextInt();

        try {
            System.out.println(genericInit.getRepairerService().changeStatus(id));
        } catch (InvalidIdException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Repairer status was successfully updated.");
    }
}
