package org.example.management.actions.repairerActions;

import org.example.exception.InvalidIdException;
import org.example.management.actions.Action;

public class FindRepairerById implements Action {
    @Override
    public void execute() {
        System.out.println("To view the repairer information please enter his id: ");

        int id = scanner.nextInt();

        try {
            System.out.println(genericInit.getRepairerService().findById(id));
        } catch (InvalidIdException e) {
            System.out.println(e.getMessage());
        }
    }
}
