package org.example.management;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ScannerHandler {

    Scanner scanner = new Scanner(System.in);

    public int nextInt() {
        String s = scanner.next();
        if (isNumber(s)) {
            return Integer.parseInt(s);
        } else {
            return -1;
        }
    }

    private boolean isNumber(String s) {
        try {
            Long.parseLong(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public String next() {
        return scanner.next();
    }

    public long nextLong() {
        String s = scanner.next();
        if (isNumber(s)) {
            return Long.parseLong(s);
        } else {
            return -1;
        }
    }
}
