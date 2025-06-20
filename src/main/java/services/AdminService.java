package services;

import entities.*;
import utils.MapDisplayer;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Scanner;

public class AdminService {
    private Map<Integer, CoworkingSpace> spaces;
    private Map<Integer, Reservation> reservations;
    private Scanner scanner;

    public AdminService(Map<Integer, CoworkingSpace> spaces, Map<Integer, Reservation> reservations, Scanner scanner) {
        this.spaces = spaces;
        this.reservations = reservations;
        this.scanner = scanner;
    }

    public void addSpace() {
        System.out.print("Enter spaces type: ");
        String type = scanner.nextLine();

        System.out.print("Enter spaces details: ");
        String details = scanner.nextLine();

        System.out.print("Enter price of the space: ");
        BigDecimal price = new BigDecimal(scanner.nextLine());

        CoworkingSpace space = new CoworkingSpace(type, details, price);
        spaces.put(space.getId(), space);
        System.out.println("Coworking space added successfully!");
    }

    public void removeSpace() {
        MapDisplayer.display(spaces);

        System.out.print("Enter space id to remove: ");
        int id = Integer.parseInt(scanner.nextLine());

        if (spaces.containsKey(id)) {
            spaces.remove(id);
            System.out.println("Space removed successfully!");
        } else {
            System.out.println("Space not found.");
        }
    }
}

