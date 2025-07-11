package app.controllers;

import app.dto.ReservationDTO;
import app.entities.CoworkingSpace;
import app.entities.Reservation;
import app.services.CustomerService;
import app.utils.CustomExceptions.wrongTimeInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/spaces")
    public List<CoworkingSpace> getAvailableSpaces() {
        return customerService.getAvailableSpaces();
    }

    @GetMapping("/reservations")
    public List<Reservation> getReservations(@RequestParam String username) {
        return customerService.getReservations(username);
    }

    @PostMapping("/reservations/new")
    public Reservation makeReservation(@RequestBody ReservationDTO reservationDTO) throws wrongTimeInputException {
        LocalDateTime startTime = LocalDateTime.parse(reservationDTO.getTimeStart());
        LocalDateTime endTime = LocalDateTime.parse(reservationDTO.getTimeEnd());
        return customerService.makeReservation(
                reservationDTO.getUsername(),
                reservationDTO.getSpaceId(),
                startTime,
                endTime
        );
    }

    @DeleteMapping("/reservations/{id}")
    public String cancelReservation(@RequestParam String username, @PathVariable Integer id) {
        boolean cancelled = customerService.cancelReservation(username, id);
        return cancelled ? "Reservation cancelled" : "Reservation not found or not yours";
    }
}
