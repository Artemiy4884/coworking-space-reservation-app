package services;

import entities.*;

import java.util.List;
import java.util.Scanner;

public class AdminService {
    private List<CoworkingSpace> spaces;
    private List<Reservation> reservations;
    private Scanner scanner;

    public AdminService(List<CoworkingSpace> spaces, List<Reservation> reservations, Scanner scanner) {
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
        double capacity = Double.parseDouble(scanner.nextLine());
        spaces.add(new CoworkingSpace(type, details, capacity));
        System.out.println("Coworking space added successfully!");
    }

    private void viewAllSpaces(){
        for (CoworkingSpace space : spaces) {
            System.out.println(space);
        }
    }

    public void removeSpace() {
        viewAllSpaces();
        System.out.print("Enter space id to remove: ");
        int id = Integer.parseInt(scanner.nextLine());
        for(CoworkingSpace space : spaces){
            if(space.getId() == id){
                spaces.remove(space);
                System.out.println("Space removed successfully!");
                return;
            }
        }
        System.out.println("Space not found.");
    }

    public void viewAllReservations() {
        if (reservations.isEmpty()) {
            System.out.println("No reservations found.");
        } else {
            for (Reservation reservation : reservations) {
                System.out.println(reservation);
            }
        }
    }
}

