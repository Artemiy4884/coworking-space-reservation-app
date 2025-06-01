package utils;

import entities.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    private static final String SPACES_FILE = "data" + File.separator + "spaces.txt";
    private static final String RESERVATIONS_FILE = "data" + File.separator + "reservations.txt";
    private static final String USERS_FILE = "data" + File.separator + "users.txt";

    public static void saveSpaces(List<CoworkingSpace> spaces) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SPACES_FILE))) {
            oos.writeObject(spaces);
        }
    }

    public static List<CoworkingSpace> loadSpaces() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SPACES_FILE))) {
            return (List<CoworkingSpace>) ois.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public static void saveReservations(List<Reservation> reservations) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(RESERVATIONS_FILE))) {
            oos.writeObject(reservations);
        }
    }

    public static List<Reservation> loadReservations() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(RESERVATIONS_FILE))) {
            return (List<Reservation>) ois.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public static void saveUsers(List<User> users) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USERS_FILE))) {
            oos.writeObject(users);
        }
    }

    public static List<User> loadUsers() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(USERS_FILE))) {
            return (List<User>) ois.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
