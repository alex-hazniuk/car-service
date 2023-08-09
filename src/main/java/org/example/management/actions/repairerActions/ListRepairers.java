package org.example.management.actions.repairerActions;

import org.example.management.actions.Action;

public class ListRepairers implements Action {
    @Override
    public void execute() {
        System.out.println(genericInit.getRepairerService().getAll());
    }
}
