package org.example.management.actions.repairerActions;

import org.example.management.actions.Action;
import org.example.management.actions.initServices.RepairerServiceInit;

public class ListRepairers extends RepairerServiceInit implements Action {
    @Override
    public void execute() {
        System.out.println(repairerService.getAll());
    }
}
