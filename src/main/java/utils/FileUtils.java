package utils;

import entities.*;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class FileUtils {
    private static final String SPACES_FILE = "data" + File.separator + "spaces.dat";
    private static final String RESERVATIONS_FILE = "data" + File.separator + "reservations.dat";
    private static final String USERS_FILE = "data" + File.separator + "users.dat";

    public static void saveSpaces(Map<Integer, CoworkingSpace> spaces) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SPACES_FILE))) {
            oos.writeObject(spaces);
        }
    }

    public static Map<Integer, CoworkingSpace> loadSpaces() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SPACES_FILE))) {
            return (Map<Integer, CoworkingSpace>) ois.readObject();
        } catch (Exception e) {
            return new HashMap<>();
        }
    }

    public static void saveReservations(Map<Integer, Reservation> reservations) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(RESERVATIONS_FILE))) {
            oos.writeObject(reservations);
        }
    }

    public static Map<Integer, Reservation> loadReservations() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(RESERVATIONS_FILE))) {
            return (Map<Integer, Reservation>) ois.readObject();
        } catch (Exception e) {
            return new HashMap<>();
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
