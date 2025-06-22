package services;

import dao.CoworkingSpaceDAO;
import entities.CoworkingSpace;
import org.junit.jupiter.api.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Map;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestAdminService {

    private AdminService adminService;
    private Scanner scannerMock;

    @BeforeEach
    void setUp() {
        scannerMock = mock(Scanner.class);
        adminService = new AdminService(scannerMock);
    }

    @Test
    @Order(1)
    @DisplayName("Test addition of coworking space through AdminService and DAO")
    void testAddSpace() throws SQLException {
        when(scannerMock.nextLine()).thenReturn("Private Office", "Room B", "150");

        int originalSize = CoworkingSpaceDAO.getAllSpaces().size();

        adminService.addSpace();

        Map<Integer, CoworkingSpace> spaces = CoworkingSpaceDAO.getAllSpaces();
        assertEquals(originalSize + 1, spaces.size());

        CoworkingSpace lastAdded = spaces.values().stream()
                .filter(s -> s.getType().equals("Private Office") && s.getRoomDetails().equals("Room B"))
                .findFirst()
                .orElse(null);

        assertNotNull(lastAdded);
        assertEquals(0, lastAdded.getPrice().compareTo(new BigDecimal("150")));

        assertTrue(lastAdded.isAvailable());

        CoworkingSpaceDAO.removeSpace(lastAdded.getId());
    }

    @Test
    @Order(2)
    @DisplayName("Test removal of coworking space through AdminService and DAO")
    void testAdminRemoveSpace() throws SQLException {
        CoworkingSpace space = new CoworkingSpace("Hot Desk", "Corner", BigDecimal.valueOf(99));
        CoworkingSpaceDAO.addSpace(space);

        when(scannerMock.nextLine()).thenReturn(String.valueOf(space.getId()));

        adminService.removeSpace();

        assertFalse(CoworkingSpaceDAO.getAllSpaces().containsKey(space.getId()));
    }
}
