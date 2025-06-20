package services;

import entities.CoworkingSpace;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestAdminService {

    private Map<Integer, CoworkingSpace> spaces;
    private AdminService adminService;
    private Scanner scannerMock;

    @BeforeEach
    void setUp() {

        scannerMock = mock(Scanner.class);
        spaces = new HashMap<>();
        Map<Integer, entities.Reservation> reservations = new HashMap<>();

        adminService = new AdminService(spaces, reservations, scannerMock);
    }

    @Test
    @DisplayName("Test of correct addition of the new coworking spaces")
    void testAddSpace() {
        when(scannerMock.nextLine()).thenReturn("Private Office", "Room B", "150");
        adminService.addSpace();
        Assertions.assertEquals(1, spaces.size());

        CoworkingSpace space = spaces.values().iterator().next();
        Assertions.assertEquals("Private Office", space.getType());
        Assertions.assertEquals("Room B", space.getRoomDetails());
        Assertions.assertEquals(new BigDecimal("150"), space.getPrice());
        Assertions.assertTrue(space.isAvailable());
    }

    @Test
    @DisplayName("Test of correct removal of coworking spaces")
    void testAdminRemoveSpace() {
        CoworkingSpace space = new CoworkingSpace("Open Desk", "Near window", BigDecimal.valueOf(50));
        spaces.put(space.getId(), space);

        when(scannerMock.nextLine()).thenReturn(String.valueOf(space.getId()));

        adminService.removeSpace();

        assertFalse(spaces.containsKey(space.getId()));
    }
}
