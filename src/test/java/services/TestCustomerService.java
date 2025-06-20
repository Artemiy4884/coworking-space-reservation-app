package services;

import entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestCustomerService {

    private Map<Integer, Reservation> reservations;
    private CustomerService customerService;
    private Map<Integer, CoworkingSpace> spaces;
    private Scanner scannerMock;
    private CoworkingSpace space;

    @BeforeEach
    void setUp() {
        spaces = new HashMap<>();
        reservations = new HashMap<>();
        scannerMock = mock(Scanner.class);
        space = new CoworkingSpace("Open Desk", "Near window", BigDecimal.valueOf(50));
        spaces.put(space.getId(), space);
        customerService = new CustomerService(spaces, reservations, scannerMock);
    }

    @Test
    @DisplayName("Test customers possibility of reservation coworking space")
    void testCustomerMakeReservation() {
        when(scannerMock.nextLine())
                .thenReturn(String.valueOf(space.getId()))
                .thenReturn("2025-06-19")
                .thenReturn("09:00")
                .thenReturn("17:00");

        customerService.makeReservation("customer1");

        assertEquals(1, reservations.size());
        assertFalse(spaces.get(space.getId()).isAvailable());
    }

    @Test
    @DisplayName("Test to check unavailability to reserve occupied coworking space")
    void testReservationWithOccupiedSpace() {
        CoworkingSpace space = new CoworkingSpace("Shared", "Desk B", new BigDecimal("10.00"));
        space.setAvailable(false);
        spaces.put(space.getId(), space);

        when(scannerMock.nextLine())
                .thenReturn(String.valueOf(space.getId()))
                .thenReturn("2025-06-26")
                .thenReturn("09:00")
                .thenReturn("11:00");

        customerService.makeReservation("John");

        assertTrue(reservations.isEmpty());
    }

    @Test
    @DisplayName("Test of user canceling the reservation")
    void testCustomerCancelReservation() {
        space.setAvailable(false);

        Reservation reservation = new Reservation("customer1", space.getId(), "2025-06-19 09:00", "2025-06-19 17:00");
        reservations.put(reservation.getReservationId(), reservation);

        when(scannerMock.nextLine()).thenReturn(String.valueOf(space.getId()));

        customerService.cancelReservation("customer1");

        assertEquals(0, reservations.size());
        assertTrue(spaces.get(space.getId()).isAvailable());
    }

    @Test
    @DisplayName("Test of failed cancel for non-existent reservation")
    void testCancelNonExistentReservation() {
        when(scannerMock.nextLine()).thenReturn("999");

        customerService.cancelReservation("Johny");

        assertTrue(reservations.isEmpty());
    }
}
