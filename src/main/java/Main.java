import dao.CoworkingSpaceDAO;
import dao.ReservationDAO;
import dao.UserDAO;
import entities.*;
import services.*;
import utils.CustomExceptions.*;
import utils.FileUtils;
import utils.MapDisplayer;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static AdminService adminService = new AdminService(scanner);
    private static CustomerService customerService = new CustomerService(scanner);

    public static void main(String[] args) {
        AtomicBoolean isWorking = new AtomicBoolean(true);

        Map<Integer, Runnable> basicActions = new HashMap<>();

        basicActions.put(1, Main::adminMenuDisplayer);
        basicActions.put(2, Main::userMenuDisplayer);
        basicActions.put(3, () -> {
            System.out.println("Do you want to exit? (yes/no)");
            String exit = scanner.nextLine();
            if (exit.equalsIgnoreCase("yes")) {
                isWorking.set(false);
            }
        });

        while (isWorking.get()) {
            System.out.println("Welcome to the Coworking Space Reservation System!");
            try {
                System.out.println("Select user type by printing a number: " +
                        "\n1. Admin" +
                        "\n2. Customer" +
                        "\n3. Exit from the program");
                Integer userType = Integer.parseInt(scanner.nextLine());

                Runnable action = basicActions.get(userType);
                if (action != null) {
                    action.run();
                } else {
                    throw new InvalidUserRoleException("Invalid user type! Please choose from existing variants");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public static void adminMenuDisplayer() {
        AtomicBoolean isWorking = new AtomicBoolean(true);

        try {
            Map<Integer, Runnable> adminActions = getAdminActions(isWorking);

            String[] userData = loginSystem();
            String username = userData[0];
            String password = userData[1];

            if (validateUser(username, password, "admin")) {
                while (isWorking.get()) {
                    System.out.println("Please, select one of the options by writing a number: " +
                            "\n1. Add new coworking space" +
                            "\n2. Remove coworking space" +
                            "\n3. View all reservations" +
                            "\n4. Exit to choosing user menu");
                    int choice = Integer.parseInt(scanner.nextLine());

                    Runnable action = adminActions.get(choice);

                    if (action != null) {
                        action.run();
                    } else {
                        throw new InvalidCredentialsException("Invalid option!");
                    }
                }
            } else {
                throw new InvalidCredentialsException("Invalid admin credentials. Please check your username and password.");
            }
        } catch (InvalidCredentialsException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    private static Map<Integer, Runnable> getAdminActions(AtomicBoolean isWorking) {
        Map<Integer, Runnable> adminActions = new HashMap<>();
        adminActions.put(1, () -> adminService.addSpace());
        adminActions.put(2, () -> adminService.removeSpace());
        adminActions.put(3, () -> {
            try {
                MapDisplayer.display(ReservationDAO.getAllReservations());
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        });
        adminActions.put(4, () -> isWorking.set(false));
        return adminActions;
    }

    public static String[] loginSystem() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        return new String[]{username, password};
    }

    public static boolean validateUser(String username, String password, String role) {
        try {
            for (User user : UserDAO.getAllUsers()) {
                if (user.getUsername().equals(username) &&
                        user.getPassword().equals(password) &&
                        user.getRole().equals(role)) {
                    return true;
                }
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static void userMenuDisplayer() {
        System.out.println("Do you have an account? (yes/no)");
        String hasAccount = scanner.nextLine();
        if (hasAccount.equalsIgnoreCase("no")) createNewUser();

        String[] userData = loginSystem();
        String username = userData[0];
        String password = userData[1];

        AtomicBoolean isWorking = new AtomicBoolean(true);

        Map<Integer, Runnable> userActions = getCustomerActions(username, isWorking);

        try {
            if (validateUser(username, password, "customer")) {
                while (isWorking.get()) {
                    System.out.println("Customer Menu: " +
                            "\n1. View all free spaces" +
                            "\n2. Reserve a coworking space" +
                            "\n3. View all reservations made by you" +
                            "\n4. Cancel a reservation" +
                            "\n5. Exit to choosing user menu");
                    int choice = Integer.parseInt(scanner.nextLine());

                    Runnable action = userActions.get(choice);
                    if (action != null) {
                        action.run();
                    } else {
                        throw new InvalidCredentialsException("Invalid option!");
                    }
                }
            } else {
                throw new InvalidCredentialsException("Invalid customer credentials. Please check your username and password.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static Map<Integer, Runnable> getCustomerActions(String username, AtomicBoolean isWorking) {
        Map<Integer, Runnable> userActions = new HashMap<>();
        userActions.put(1, () -> {
            try {
                MapDisplayer.display(CoworkingSpaceDAO.getAllSpaces());
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        });
        userActions.put(2, () -> customerService.makeReservation(username));
        userActions.put(3, () -> customerService.viewMyReservations(username));
        userActions.put(4, () -> customerService.cancelReservation(username));
        userActions.put(5, () -> isWorking.set(false));
        return userActions;
    }

    public static void createNewUser() {
        try {
            System.out.print("Enter new username: ");
            String newUsername = scanner.nextLine();
            List<User> users = UserDAO.getAllUsers();
            for (User user : users) {
                if (user.getUsername().equalsIgnoreCase(newUsername)) {
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