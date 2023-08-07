package org.example.management.actions;

import org.example.management.ScannerHandler;

public interface Action {
    ScannerHandler scanner = new ScannerHandler();

    void execute();
}
