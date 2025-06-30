package app.ui;

import app.entities.CoworkingSpace;
import app.entities.Reservation;
import app.services.AuthService;
import app.services.CustomerService;
import app.utils.CollectionsDisplayer;
import app.utils.CustomExceptions.DuplicateUsernameException;
import app.utils.CustomExceptions.wrongTimeInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class CustomerConsoleWorkflow {

    private final ConsoleUI console;
    private final CustomerService customerService;
    private final AuthService authService;
    private final CollectionsDisplayer displayer;

    @Autowired
    public CustomerConsoleWorkflow(ConsoleUI console, CustomerService customerService, AuthService authService) {
        this.console = console;
        this.customerService = customerService;
        this.authService = authService;
        this.displayer = new CollectionsDisplayer(console);
    }

    public void run() {
        String hasAccount = console.prompt("Do you have an account? (yes/no): ");
        if (hasAccount.equalsIgnoreCase("no")) {
            createAccount();
        }

        String username = console.prompt("Enter your username: ");
        String password = console.prompt("Enter your password: ");

        if (!authService.validateUser(username, password, "customer")) {
            console.printError("Invalid customer credentials.");
            return;
        }

        AtomicBoolean inCustomerMenu = new AtomicBoolean(true);
        while (inCustomerMenu.get()) {
            console.println("\nCustomer Menu:\n" +
                    "1. View all free spaces\n" +
                    "2. Make a reservation\n" +
                    "3. View your reservations\n" +
                    "4. Cancel a reservation\n" +
                    "5. Exit");

            int choice = console.promptInt("Choose option: ");

            switch (choice) {
                case 1 -> {
                    List<CoworkingSpace> freeSpaces = customerService.getAvailableSpaces();
                    if (freeSpaces.isEmpty()) {
                        console.println("No available spaces.");
                    } else {
                        displayer.displayList(freeSpaces);
                    }
                }
                case 2 -> makeReservation(username);
                case 3 -> displayReservations(username);
                case 4 -> {
                    displayReservations(username);
                    int resId = console.promptInt("Enter reservation ID to cancel: ");
                    boolean cancelled = customerService.cancelReservation(username, resId);
                    console.println(cancelled ? "Reservation cancelled." : "Reservation not found or not yours.");
                }
                case 5 -> inCustomerMenu.set(false);
                default -> console.printError("Invalid option.");
            }
        }
    }

    private void createAccount() {
        while (true) {
            try {
                String newUsername = console.prompt("Enter new username: ");
                String newPassword = console.prompt("Enter new password: ");
                authService.createUser(newUsername, newPassword);
                console.println("Account created successfully. You can now log in.");
                break;
            } catch (DuplicateUsernameException e) {
                console.printError(e.getMessage());
            }
        }
    }

    private void makeReservation(String username) {
        try {
            int spaceId = console.promptInt("Enter space ID: ");
            String date = console.prompt("Enter date (yyyy-MM-dd): ");
            String startTime = console.prompt("Enter start time (HH:mm): ");
            String endTime = console.prompt("Enter end time (HH:mm): ");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime start = LocalDateTime.parse(date + " " + startTime, formatter);
            LocalDateTime end = LocalDateTime.parse(date + " " + endTime, formatter);

            customerService.makeReservation(username, spaceId, start, end);
            console.println("Reservation created successfully.");
        } catch (wrongTimeInputException e) {
            console.printError("Invalid time range: " + e.getMessage());
        } catch (Exception e) {
            console.printError("Error: " + e.getMessage());
        }
    }

    private void displayReservations(String username){
            List<Reservation> reservations = customerService.getReservations(username);
            if (reservations.isEmpty()) {
                console.println("You have no reservations.");
            } else {
                displayer.displayList(reservations);
            }
    }
}

