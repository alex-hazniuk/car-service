package org.example.management.menu;

import lombok.RequiredArgsConstructor;
import org.example.management.ScannerHandler;

@RequiredArgsConstructor
public class MenuController {

    private final Builder builder;

    private final Navigator navigator;

    public void run() {

        builder.buildMenu();
        navigator.setCurrentMenu(builder.getRootMenu());
        navigator.printMenu();
        ScannerHandler scanner = new ScannerHandler();

        while (true) {
            int index = scanner.nextInt();
            navigator.navigate(index);
            navigator.printMenu();
        }
    }
}
