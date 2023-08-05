package org.example.management.actions.repairerActions;

import org.example.management.actions.Action;
import org.example.management.actions.initServices.RepairerServiceInit;
import org.example.model.Repairer;
import org.example.model.RepairerStatus;

public class ChangeStatus extends RepairerServiceInit implements Action {

    @Override
    public void execute() {
        System.out.println("To change repairer status please enter repairers id, name : \n ID: ");

        int id = scanner.nextInt();

        System.out.println("name: ");
        String name = scanner.next();

        System.out.println("""
                Please select a new repairer status :\s
                 1. Available
                 2. Busy""");
        int status = scanner.nextInt();

        Repairer repairer = new Repairer();
        repairer.setId(id);
        repairer.setName(name);

        if (status == 1) {
            repairer.setStatus(RepairerStatus.AVAILABLE);
        } else {
            repairer.setStatus(RepairerStatus.BUSY);
        }

        System.out.println(repairerService.changeStatus(repairer));
        System.out.println("Repairer status was successfully updated.");
    }
}
