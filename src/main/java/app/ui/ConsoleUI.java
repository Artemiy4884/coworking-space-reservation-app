package app.ui;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleUI {

    private final Scanner scanner = new Scanner(System.in);

    public String prompt(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    public int promptInt(String message) {
        System.out.print(message);
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1; // Invalid input
        }
    }

    public void println(String message) {
        System.out.println(message);
    }

    public void printError(String message) {
        System.err.println("Error: " + message);
    }
}
