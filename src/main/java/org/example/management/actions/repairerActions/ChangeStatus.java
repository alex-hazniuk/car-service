package org.example.management.actions.repairerActions;

import org.example.management.actions.Action;
import org.example.management.actions.initServices.RepairerServiceInit;
public class ChangeStatus extends RepairerServiceInit implements Action {

    @Override
    public void execute() {
        System.out.println("To change repairer status please enter repairers id: \n ID: ");

        int id = scanner.nextInt();

        System.out.println(repairerService.changeStatus(id));
        System.out.println("Repairer status was successfully updated.");
    }
}
