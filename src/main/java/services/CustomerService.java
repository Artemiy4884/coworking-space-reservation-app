package services;

import entities.*;
import utils.MapDisplayer;

import java.util.List;
import java.util.Map;
import java.util.Optional;
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

        Optional<CoworkingSpace> optionalSpace = Optional.ofNullable(spaces.get(spaceId));
        if (optionalSpace.isEmpty()) {
            System.out.println("Space with id " + spaceId + " not found.");
            return;
        }

        CoworkingSpace space = optionalSpace.get();
        if (!space.isAvailable()) {
            System.out.println("Space is not available.");
            return;
        }
        space.setAvailable(false);

        Reservation reservation = new Reservation(username, spaceId, startTime, endTime);
        reservations.put(reservation.getReservationId(), reservation);

        System.out.println("Reservation successful.");
    }

    public void viewMyReservations(String username) {
        List<Reservation> userReservations = reservations.values().stream()
                .filter(res -> res.getUsername().equalsIgnoreCase(username))
                .toList();

        if (userReservations.isEmpty()) {
            System.out.println("You have no reservations.");
        } else {
            userReservations.forEach(res -> {
                System.out.println(res);
                System.out.println("----------------------------");
            });
        }
    }


    public void cancelReservation(String username) {
        viewMyReservations(username);

        System.out.print("Enter space id to cancel reservation: ");
        int spaceId = Integer.parseInt(scanner.nextLine());

        Optional<Map.Entry<Integer, Reservation>> toRemove = reservations.entrySet().stream()
                .filter(e -> e.getValue().getUsername().equalsIgnoreCase(username) && e.getValue().getSpaceId() == spaceId)
                .findAny();

        if (toRemove.isPresent()) {
            reservations.remove(toRemove.get().getKey());
            Optional.ofNullable(spaces.get(spaceId)).ifPresent(space -> space.setAvailable(true));
            System.out.println("Reservation canceled.");
        } else {
            System.out.println("Reservation not found.");
        }
    }


}

