package org.example.management.actions;

import java.util.Scanner;

public interface Action {
    Scanner scanner = new Scanner(System.in);

    void execute();
}
