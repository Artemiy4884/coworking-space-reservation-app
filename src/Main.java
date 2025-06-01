import entities.*;
import services.*;
import utils.CustomExceptions.*;
import utils.FileUtils;

import java.util.*;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static List<CoworkingSpace> spaces = FileUtils.loadSpaces();
    private static List<Reservation> reservations = FileUtils.loadReservations();
    private static List<User> users = FileUtils.loadUsers();
    private static AdminService adminService = new AdminService(spaces, reservations, scanner);
    private static CustomerService customerService = new CustomerService(spaces, reservations, scanner);

    public static void main(String[] args) {
        CoworkingSpace.initializeNextId(spaces);
        Reservation.initializeNextId(reservations);

        boolean isWorking = true;
        while (isWorking) {
            System.out.println("Welcome to the Coworking Space Reservation System!");
            try {
                System.out.println("Select user type by printing a number: " +
                        "\n1. Admin" +
                        "\n2. Customer" +
                        "\n3. Exit from the program");
                String userType = scanner.nextLine();

                switch (userType) {
                    case "1":
                        adminMenuDisplayer();
                        break;
                    case "2":
                        userMenuDisplayer();
                        break;
                    case "3":
                        System.out.println("Do you want to exit? (yes/no)");
                        String exit = scanner.nextLine();
                        if (exit.equalsIgnoreCase("yes")) {
                            try {
                                FileUtils.saveSpaces(spaces);
                                FileUtils.saveReservations(reservations);
                                FileUtils.saveUsers(users);
                            } catch (Exception e) {
                                System.out.println("Error saving data: " + e.getMessage());
                            }
                            isWorking = false;
                        }
                        break;
                    default:
                        throw new InvalidUserRoleException("Unknown selection. Please enter 1 or 2.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public static String[] loginSystem() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        return new String[]{username, password};
    }

    public static boolean validateUser(String username, String password, String role) {
        for (User user : users) {
            if (user.getUsername().equals(username) &&
                    user.getPassword().equals(password) &&
                    user.getRole().equals(role)) {
                return true;
            }
        }
        return false;
    }

    public static void adminMenuDisplayer() {
        String[] userData = loginSystem();
        String username = userData[0];
        String password = userData[1];

        try {
            if (validateUser(username, password, "admin")) {
                boolean isWorking = true;
                while (isWorking) {
                    System.out.println("Please, select one of the options by writing a number: " +
                            "\n1. Add new coworking space" +
                            "\n2. Remove coworking space" +
                            "\n3. View all reservations" +
                            "\n4. Exit to choosing user menu");
                    int choice = Integer.parseInt(scanner.nextLine());

                    switch (choice) {
                        case 1:
                            adminService.addSpace();
                            break;
                        case 2:
                            adminService.removeSpace();
                            break;
                        case 3:
                            adminService.viewAllReservations();
                            break;
                        case 4:
                            isWorking = false;
                            break;
                        default:
                            throw new InvalidCredentialsException("Invalid option. Please choose the number of one of the options!");
                    }
                }
            } else {
                throw new InvalidCredentialsException("Invalid admin credentials. Please check your username and password.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    public static void userMenuDisplayer() {
        System.out.println("Do you have an account? (yes/no)");
        String hasAccount = scanner.nextLine();
        if (hasAccount.equalsIgnoreCase("no")) createNewUser();

        String[] userData = loginSystem();
        String username = userData[0];
        String password = userData[1];

        try {
            if (validateUser(username, password, "customer")) {
                boolean isWorking = true;
                while (isWorking) {
                    System.out.println("Customer Menu: " +
                            "\n1. View all free spaces" +
                            "\n2. Reserve a coworking space" +
                            "\n3. View all reservations made by you" +
                            "\n4. Cancel a reservation" +
                            "\n5. Exit to choosing user menu");
                    int choice = Integer.parseInt(scanner.nextLine());

                    switch (choice) {
                        case 1:
                            customerService.viewAllFreeSpaces();
                            break;
                        case 2:
                            customerService.makeReservation(username);
                            break;
                        case 3:
                            customerService.viewMyReservations(username);
                            break;
                        case 4:
                            customerService.cancelReservation(username);
                            break;
                        case 5:
                            isWorking = false;
                            break;
                        default:
                            throw new InvalidCredentialsException("Invalid option. Please choose the number of one of the options!");
                    }
                }
            } else {
                throw new InvalidCredentialsException("Invalid customer credentials. Please check your username and password.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void createNewUser() {
        try {
            System.out.print("Enter new username: ");
            String newUsername = scanner.nextLine();
            for (User user : users) {
                if (user.getUsername().equals(newUsername)) {
                    throw new DuplicateUsernameException("User with such username already exists. Please try again with other username.");
                }
            }
            System.out.print("Enter new password: ");
            String newPassword = scanner.nextLine();
            users.add(new User(newUsername, newPassword, "customer"));
            FileUtils.saveUsers(users);
            System.out.println("Account created successfully. You can now log in.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}