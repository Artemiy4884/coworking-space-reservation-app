package dao;

import entities.Reservation;

import java.sql.*;
import java.util.*;

public class ReservationDAO {

    public static Map<Integer, Reservation> getAllReservations() throws SQLException {
        Map<Integer, Reservation> reservations = new HashMap<>();
        String sql = "SELECT * FROM reservations";

        try (Connection connection = DBConnector.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resSet = statement.executeQuery(sql)) {

            while (resSet.next()) {
                Reservation reservation = new Reservation(
                        resSet.getString("username"),
                        resSet.getInt("space_id"),
                        resSet.getTimestamp("start_time").toLocalDateTime(),
                        resSet.getTimestamp("end_time").toLocalDateTime()
                );
                reservation.setReservationId(resSet.getInt("reservation_id"));
                reservations.put(resSet.getInt("reservation_id"), reservation);
            }
        }

        return reservations;
    }

    public static void addReservation(Reservation reservation) throws SQLException {
        String sql = "INSERT INTO reservations (username, space_id, start_time, end_time) VALUES (?, ?, ?, ?)";

        try (Connection connection = DBConnector.getConnection();
             PreparedStatement prepStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            prepStatement.setString(1, reservation.getUsername());
            prepStatement.setInt(2, reservation.getSpaceId());
            prepStatement.setTimestamp(3, Timestamp.valueOf(reservation.getStartTime()));
            prepStatement.setTimestamp(4, Timestamp.valueOf(reservation.getEndTime()));

            prepStatement.executeUpdate();

            try (ResultSet keys = prepStatement.getGeneratedKeys()) {
                if (keys.next()) {
                    int generatedId = keys.getInt(1);
                    reservation.setReservationId(generatedId);
                }
            }
        }
    }


    public static void removeReservation(int reservationId) throws SQLException {
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement prepStatement = connection.prepareStatement("DELETE FROM reservations WHERE reservation_id = ?")) {
            prepStatement.setInt(1, reservationId);
            prepStatement.executeUpdate();
        }
    }
}
