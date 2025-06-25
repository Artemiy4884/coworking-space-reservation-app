package services;

import dao.CoworkingSpaceDAO;
import entities.*;
import utils.MapDisplayer;

import java.math.BigDecimal;
import java.util.Scanner;

public class AdminService {
    private Scanner scanner;

    public AdminService(Scanner scanner) {
        this.scanner = scanner;
    }

    public void addSpace() {
        System.out.print("Enter spaces type: ");
        String type = scanner.nextLine();

        System.out.print("Enter spaces details: ");
        String details = scanner.nextLine();

        System.out.print("Enter price of the space: ");
        BigDecimal price = new BigDecimal(scanner.nextLine());
        CoworkingSpaceDAO.addSpace(new CoworkingSpace(type, details, price));
        System.out.println("Coworking space added successfully!");
    }

    public void removeSpace() {
        MapDisplayer.display(CoworkingSpaceDAO.getAllSpaces());

        System.out.print("Enter space id to remove: ");
        int id = Integer.parseInt(scanner.nextLine());

        if (CoworkingSpaceDAO.removeSpace(id)) {
            System.out.println("Space removed successfully!");
        } else {
            System.out.println("Space not found.");
        }
    }
}

