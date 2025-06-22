package services;

import dao.*;
import entities.*;
import org.junit.jupiter.api.*;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestCustomerService {

    private CustomerService customerService;
    private Scanner scannerMock;

    @BeforeEach
    void setUp() {
        scannerMock = mock(Scanner.class);
        customerService = new CustomerService(scannerMock);
        try {
            UserDAO.addUser(new User("customer1", "test", "customer"));
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

    }

    @Test
    @Order(1)
    @DisplayName("Test customer makes a reservation")
    void testCustomerMakeReservation() throws SQLException {
        CoworkingSpace space = new CoworkingSpace("Open Desk", "Near window", BigDecimal.valueOf(50));
        CoworkingSpaceDAO.addSpace(space);

        when(scannerMock.nextLine())
                .thenReturn(String.valueOf(space.getId()))
                .thenReturn("2025-06-25")
                .thenReturn("09:00")
                .thenReturn("17:00");

        customerService.makeReservation("customer1");

        Map<Integer, Reservation> reservations = ReservationDAO.getAllReservations();
        boolean found = reservations.values().stream()
                .anyMatch(r -> r.getUsername().equals("customer1") && r.getSpaceId() == space.getId());

        assertTrue(found);
        assertFalse(CoworkingSpaceDAO.getAllSpaces().get(space.getId()).isAvailable());

        reservations.values().stream()
                .filter(r -> r.getUsername().equals("customer1"))
                .forEach(r -> {
                    try {
                        ReservationDAO.removeReservation(r.getReservationId());
                    } catch (SQLException ignored) {}
                });

        CoworkingSpaceDAO.removeSpace(space.getId());
    }

    @Test
    @Order(2)
    @DisplayName("Test customer cannot reserve an unavailable space")
    void testReservationWithOccupiedSpace() throws SQLException {
        CoworkingSpace space = new CoworkingSpace("Shared", "Desk B", BigDecimal.valueOf(20));
        space.setAvailable(false);
        CoworkingSpaceDAO.addSpace(space);

        when(scannerMock.nextLine())
                .thenReturn(String.valueOf(space.getId()))
                .thenReturn("2025-06-26")
                .thenReturn("09:00")
                .thenReturn("11:00");

        customerService.makeReservation("customer1");

        Map<Integer, Reservation> reservations = ReservationDAO.getAllReservations();
        boolean exists = reservations.values().stream()
                .anyMatch(r -> r.getUsername().equals("customer1"));

        assertFalse(exists);

        CoworkingSpaceDAO.removeSpace(space.getId());
    }

    @Test
    @Order(3)
    @DisplayName("Test customer cancels a reservation")
    void testCustomerCancelReservation() throws SQLException {
        CoworkingSpace space = new CoworkingSpace("Private Room", "Room 3", BigDecimal.valueOf(100));
        CoworkingSpaceDAO.addSpace(space);

        LocalDateTime start = LocalDateTime.of(2025, 6, 19, 9, 0);
        LocalDateTime end = LocalDateTime.of(2025, 6, 19, 17, 0);

        Reservation reservation = new Reservation("customer1", space.getId(), start, end);
        ReservationDAO.addReservation(reservation);

        CoworkingSpaceDAO.updateAvailability(space.getId(), false);

        when(scannerMock.nextLine()).thenReturn(String.valueOf(space.getId()));

        customerService.cancelReservation("customer1");

        Map<Integer, Reservation> reservations = ReservationDAO.getAllReservations();
        boolean stillExists = reservations.values().stream()
                .anyMatch(r -> r.getUsername().equals("customer1"));

        assertFalse(stillExists);
        assertTrue(CoworkingSpaceDAO.getAllSpaces().get(space.getId()).isAvailable());

        CoworkingSpaceDAO.removeSpace(space.getId());
    }

    @Test
    @Order(4)
    @DisplayName("Test canceling non-existent reservation")
    void testCancelNonExistentReservation() throws SQLException {
        when(scannerMock.nextLine()).thenReturn("9999999");
        customerService.cancelReservation("unknownUser");
        assertTrue(true);
    }

    @AfterEach
    void cleanUp(){
        try (Connection connection = DBConnector.getConnection();
            PreparedStatement prepStatement = connection.prepareStatement("DELETE FROM users WHERE username = ?")) {
            prepStatement.setString(1, "customer1");
            prepStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
