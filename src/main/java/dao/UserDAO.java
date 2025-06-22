package dao;

import entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public static List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Connection connection = DBConnector.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resSet = statement.executeQuery(sql)) {

            while (resSet.next()) {
                users.add(new User(
                        resSet.getString("username"),
                        resSet.getString("password"),
                        resSet.getString("role")
                ));
            }
        }

        return users;
    }

    public static void addUser(User user) throws SQLException {
        String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";

        try (Connection connection = DBConnector.getConnection();
             PreparedStatement prepStatement = connection.prepareStatement(sql)) {

            prepStatement.setString(1, user.getUsername());
            prepStatement.setString(2, user.getPassword());
            prepStatement.setString(3, user.getRole());
            prepStatement.executeUpdate();
        }
    }
}
