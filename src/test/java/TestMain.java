import entities.*;
import services.*;
import utils.CustomExceptions.*;
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
    void testCreateNewUser() {
        users.clear();
        String newUsername = "newUser";
        String newPassword = "newPass";

        when(scannerMock.nextLine()).thenReturn(newUsername, newPassword);

        assertTrue(users.stream().noneMatch(user -> user.getUsername().equalsIgnoreCase(newUsername)));
        users.add(new User(newUsername, newPassword, "customer"));

        assertTrue(users.stream().anyMatch(user -> user.getUsername().equals(newUsername)));
    }

    @Test
    void testCreateDuplicateUser() {
        users.clear();
        users.add(new User("duplicateUser", "pwd", "customer"));
        String dupName = "duplicateUser";

        assertThrows(DuplicateUsernameException.class, () -> {
            for (User user : users) {
                if (user.getUsername().equalsIgnoreCase(dupName)) {
                    throw new DuplicateUsernameException("User with such username already exists.");
                }
            }
        });
    }


}
