package services;

import entities.*;

import java.util.List;
import java.util.Scanner;

public class CustomerService {
    private List<CoworkingSpace> spaces;
    private List<Reservation> reservations;
    private Scanner scanner;

    public CustomerService(List<CoworkingSpace> spaces, List<Reservation> reservations, Scanner scanner) {
        this.spaces = spaces;
        this.reservations = reservations;
        this.scanner = scanner;
    }

    public void viewAllFreeSpaces() {
        for (CoworkingSpace space : spaces) {
            if(space.isAvailable()){
                System.out.println("----------------------------");
                System.out.println(space);
            }
        }
    }

    public void makeReservation(String username) {
        viewAllFreeSpaces();
        System.out.print("Enter spaces id you want to reserve: ");
        int spaceId = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter the date you want to reserve: ");
        String date = scanner.nextLine();
        System.out.println("Enter the time you want to start on: ");
        String startTime = date + " " + scanner.nextLine();
        System.out.println("Enter the time you want to end on: ");
        String endTime = date + " " + scanner.nextLine();

        for (CoworkingSpace space : spaces) {
            if (space.getId() == spaceId) {
                space.setAvailable(false);
                reservations.add(new Reservation(username, spaceId, startTime, endTime));
                System.out.println("Reservation successful.");
                return;
            }
        }
    }

    public void viewMyReservations(String username) {
        boolean found = false;
        for (Reservation reservation : reservations) {
            if (reservation.getUsername().equals(username)) {
                System.out.println("----------------------------");
                System.out.println(reservation);
                found = true;
            }
        }
        if (!found) {
            System.out.println("You have no reservations.");
        }
    }

    public void cancelReservation(String username) {
        viewMyReservations(username);
        System.out.print("Enter spaces id to cancel reservation: ");
        int spaceId = Integer.parseInt(scanner.nextLine());

        for (Reservation reservation : reservations) {
            if (reservation.getSpaceId() == spaceId) {
                reservations.remove(reservation);
                for (CoworkingSpace space : spaces) {
                    if (space.getId() == spaceId) {
                        space.setAvailable(true);
                        break;
                    }
                }
                System.out.println("Reservation canceled.");
                return;
            }
        }
        System.out.println("Reservation not found.");
    }
}

