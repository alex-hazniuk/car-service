package org.example.management.actions;

import org.example.management.ScannerHandler;
import org.example.management.actions.initServices.GenericInit;

public interface Action {
    GenericInit genericInit = new GenericInit();

    ScannerHandler scanner = new ScannerHandler();

    void execute();
}
