package org.example.management.actions;

import org.example.management.ScannerHandler;
import org.example.management.actions.initServices.GenericInit;

public abstract class Action {

    protected GenericInit genericInit;

    protected ScannerHandler scanner;


    public abstract void execute();
}
