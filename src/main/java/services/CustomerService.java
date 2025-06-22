package services;

import dao.CoworkingSpaceDAO;
import dao.ReservationDAO;
import entities.*;
import utils.MapDisplayer;
import utils.CustomExceptions.wrongTimeInputException;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public class CustomerService {
    private Scanner scanner;

    public CustomerService(Scanner scanner) {

        this.scanner = scanner;
    }

    public void makeReservation(String username) {
        try {
            MapDisplayer.display(CoworkingSpaceDAO.getAllSpaces());

            System.out.print("Enter spaces id you want to reserve: ");
            int spaceId = Integer.parseInt(scanner.nextLine());

            System.out.println("Enter the date you want to reserve in the format yyyy-mm-dd: ");
            String date = scanner.nextLine();

            System.out.println("Enter the time you want to start on in the format HH:MM: ");
            String startTime = date + " " + scanner.nextLine();

            System.out.println("Enter the time you want to end on in the format HH:MM: ");
            String endTime = date + " " + scanner.nextLine();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            LocalDateTime locStartTime = LocalDateTime.parse(startTime, formatter);
            LocalDateTime locEndTime = LocalDateTime.parse(endTime, formatter);

            if (!locEndTime.isAfter(locStartTime)) {
                throw new wrongTimeInputException("End time must be after start time.");
            }

            Optional<CoworkingSpace> optionalSpace = Optional.ofNullable(CoworkingSpaceDAO.getAllSpaces().get(spaceId));
            if (optionalSpace.isEmpty()) {
                System.out.println("Space with id " + spaceId + " not found.");
                return;
            }

            CoworkingSpace space = optionalSpace.get();
            if (!space.isAvailable()) {
                System.out.println("Space is not available.");
                return;
            }

            ReservationDAO.addReservation(new Reservation(username, spaceId, locStartTime, locEndTime));
            CoworkingSpaceDAO.updateAvailability(spaceId, false);
            System.out.println("Reservation successful.");
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date or time format. Please use yyyy-MM-dd for the date and HH:mm for time.");
        } catch (wrongTimeInputException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void viewMyReservations(String username) {
        try {
            List<Reservation> userReservations = ReservationDAO.getAllReservations().values().stream()
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
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void cancelReservation(String username) {
        viewMyReservations(username);

        System.out.print("Enter space id to cancel reservation: ");
        int spaceId = Integer.parseInt(scanner.nextLine());

        try {
            Optional<Map.Entry<Integer, Reservation>> toRemove = ReservationDAO.getAllReservations().entrySet().stream()
                    .filter(e -> e.getValue().getUsername().equalsIgnoreCase(username)
                    && e.getValue().getSpaceId() == spaceId).findAny();

            if (toRemove.isPresent()) {
                int reservationId = toRemove.get().getKey();
                int spaceIdToFree = toRemove.get().getValue().getSpaceId();

                CoworkingSpaceDAO.updateAvailability(spaceIdToFree, true);
                ReservationDAO.removeReservation(reservationId);

                System.out.println("Reservation canceled.");
            } else {
                System.out.println("Reservation not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error while canceling reservation: " + e.getMessage());
        }
    }

}

