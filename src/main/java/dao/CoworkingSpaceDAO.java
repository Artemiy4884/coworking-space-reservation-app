package dao;

import entities.CoworkingSpace;

import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

public class CoworkingSpaceDAO {

    public static Map<Integer, CoworkingSpace> getAllSpaces() throws SQLException {
        Map<Integer, CoworkingSpace> spaces = new HashMap<>();
        String sql = "SELECT * FROM coworking_spaces";

        try (Connection connection = DBConnector.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resSet = statement.executeQuery(sql)) {

            while (resSet.next()) {
                CoworkingSpace space = new CoworkingSpace(
                        resSet.getString("type"),
                        resSet.getString("room_details"),
                        resSet.getBigDecimal("price")
                );
                space.setId(resSet.getInt("id"));
                space.setAvailable(resSet.getBoolean("is_available"));
                spaces.put(space.getId(), space);
            }
        }

        return spaces;
    }

    public static void addSpace(CoworkingSpace space) throws SQLException {
        String sql = "INSERT INTO coworking_spaces (type, room_details, price, is_available) VALUES (?, ?, ?, ?)";

        try (Connection connection = DBConnector.getConnection();
             PreparedStatement prepStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            prepStatement.setString(1, space.getType());
            prepStatement.setString(2, space.getRoomDetails());
            prepStatement.setBigDecimal(3, space.getPrice());
            prepStatement.setBoolean(4, space.isAvailable());

            prepStatement.executeUpdate();

            try (ResultSet generatedKeys = prepStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    space.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    public static boolean removeSpace(int id) throws SQLException {
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement prepStatement = connection.prepareStatement(
                     "DELETE FROM coworking_spaces WHERE id = ?")) {

            prepStatement.setInt(1, id);
            int rowsAffected = prepStatement.executeUpdate();

            return rowsAffected > 0;
        }
    }

    public static void updateAvailability(int id, boolean isAvailable) throws SQLException {
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement prepStatement = connection.prepareStatement("UPDATE coworking_spaces SET is_available = ? WHERE id = ?")) {
            prepStatement.setBoolean(1, isAvailable);
            prepStatement.setInt(2, id);
            prepStatement.executeUpdate();
        }
    }
}
