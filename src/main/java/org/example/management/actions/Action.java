package org.example.management.actions;

import org.example.management.ScannerHandler;
import org.example.management.actions.initServices.GenericInit;

public abstract class Action {

    protected final GenericInit genericInit = new GenericInit();

    protected final ScannerHandler scanner = new ScannerHandler();

    public abstract void execute();
}
