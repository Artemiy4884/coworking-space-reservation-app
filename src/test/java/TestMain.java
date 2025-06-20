import entities.*;
import org.junit.jupiter.api.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestMain {

    private List<User> users;
    private Scanner scannerMock;

    @BeforeEach
    void setup() {
        users = new ArrayList<>();
        scannerMock = mock(Scanner.class);
    }

    @Test
    @DisplayName("Test of creation account for the user")
    void testCreateNewUser() {
        users.clear();
        String newUsername = "newUser";
        String newPassword = "newPass";

        when(scannerMock.nextLine()).thenReturn(newUsername, newPassword);

        assertTrue(users.stream().noneMatch(user -> user.getUsername().equalsIgnoreCase(newUsername)));
        users.add(new User(newUsername, newPassword, "customer"));

        assertTrue(users.stream().anyMatch(user -> user.getUsername().equals(newUsername)));
    }


}
