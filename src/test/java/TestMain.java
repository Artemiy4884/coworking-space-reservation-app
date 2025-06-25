import utils.DBConnector;
import dao.UserDAO;
import entities.User;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestMain {

    @BeforeEach
    void cleanUpBefore() throws SQLException {
        try (var connection = DBConnector.getConnection();
             var statement = connection.prepareStatement("DELETE FROM users WHERE username = ?")) {

            statement.setString(1, TEST_USERNAME);
            statement.executeUpdate();

            statement.setString(1, "newUser");
            statement.executeUpdate();
        }
    }

    private static final String TEST_USERNAME = "testUser123";
    private static final String TEST_PASSWORD = "securePass";
    private static final String TEST_ROLE = "customer";

    @Test
    @Order(1)
    @DisplayName("Test of adding new user with DAO")
    void testAddUser() throws SQLException {
        UserDAO.addUser(new User(TEST_USERNAME, TEST_PASSWORD, "customer"));

        List<User> users = UserDAO.getAllUsers();
        boolean exists = users.stream()
                .anyMatch(u -> u.getUsername().equals(TEST_USERNAME)
                        && u.getPassword().equals(TEST_PASSWORD)
                        && u.getRole().equals(TEST_ROLE));

        assertTrue(exists, "User should exist in DB after insertion");
    }

    @Test
    @Order(2)
    @DisplayName("Test user list incremental after adding new user with DAO")
    void testUserListGrowth() throws SQLException {
        int initialSize = UserDAO.getAllUsers().size();

        User user = new User("newUser", TEST_PASSWORD, TEST_ROLE);
        UserDAO.addUser(user);

        int newSize = UserDAO.getAllUsers().size();

        assertEquals(initialSize + 1, newSize, "User list should grow by 1");
    }

}
