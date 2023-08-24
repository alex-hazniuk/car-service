package org.example.management.actions;

public class Exit extends Action {
    @Override
    public void execute() {
        System.out.println("*** All data of your application is saved. The program finishes its execution.***");
        System.exit(0);
    }
}
