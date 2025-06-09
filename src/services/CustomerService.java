package services;

import entities.*;
import utils.MapDisplayer;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CustomerService {
    private Map<Integer, CoworkingSpace> spaces;
    private Map<Integer, Reservation> reservations;
    private Scanner scanner;

    public CustomerService(Map<Integer, CoworkingSpace> spaces, Map<Integer, Reservation> reservations, Scanner scanner) {
        this.spaces = spaces;
        this.reservations = reservations;
        this.scanner = scanner;
    }

    public void makeReservation(String username) {
        MapDisplayer.display(spaces);

        System.out.print("Enter spaces id you want to reserve: ");
        int spaceId = Integer.parseInt(scanner.nextLine());

        System.out.println("Enter the date you want to reserve: ");
        String date = scanner.nextLine();

        System.out.println("Enter the time you want to start on: ");
        String startTime = date + " " + scanner.nextLine();

        System.out.println("Enter the time you want to end on: ");
        String endTime = date + " " + scanner.nextLine();

        CoworkingSpace space = spaces.get(spaceId);
        if (space != null) {
            if (!space.isAvailable()) {
                System.out.println("Space is not available.");
                return;
            }
            space.setAvailable(false);

            // Assuming you have a reservationIdCounter to generate unique IDs
            Reservation reservation = new Reservation(username, spaceId, startTime, endTime);
            reservations.put(reservation.getReservationId(), reservation);

            System.out.println("Reservation successful.");
        } else {
            System.out.println("Space with id " + spaceId + " not found.");
        }
    }

    public void viewMyReservations(String username) {
        boolean found = false;

        for (Reservation reservation : reservations.values()) {
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

        System.out.print("Enter space id to cancel reservation: ");
        int spaceId = Integer.parseInt(scanner.nextLine());

        Integer reservationKeyToRemove = null;

        for (Map.Entry<Integer, Reservation> entry : reservations.entrySet()) {
            Reservation reservation = entry.getValue();

            if (reservation.getSpaceId() == spaceId && reservation.getUsername().equals(username)) {
                reservationKeyToRemove = entry.getKey();
                break;
            }
        }

        if (reservationKeyToRemove != null) {
            reservations.remove(reservationKeyToRemove);

            CoworkingSpace space = spaces.get(spaceId);
            if (space != null) {
                space.setAvailable(true);
            }

            System.out.println("Reservation canceled.");
        } else {
            System.out.println("Reservation not found.");
        }
    }

}

