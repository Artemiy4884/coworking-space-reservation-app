package app.controllers;

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

    @GetMapping("/spaces/available")
    public List<CoworkingSpace> getAvailableSpaces() {
        return customerService.getAvailableSpaces();
    }

    @GetMapping("/reservations")
    public List<Reservation> getReservations(@RequestParam String username) {
        return customerService.getReservations(username);
    }

    @PostMapping("/reservations/new")
    public Reservation makeReservation(@RequestParam String username,
                                       @RequestParam Integer spaceId,
                                       @RequestParam String start,
                                       @RequestParam String end) throws wrongTimeInputException {
        LocalDateTime startTime = LocalDateTime.parse(start);
        LocalDateTime endTime = LocalDateTime.parse(end);
        return customerService.makeReservation(username, spaceId, startTime, endTime);
    }

    @DeleteMapping("/reservations/cancel/{id}")
    public String cancelReservation(@RequestParam String username, @PathVariable Integer id) {
        boolean cancelled = customerService.cancelReservation(username, id);
        return cancelled ? "Reservation cancelled" : "Reservation not found or not yours";
    }
}
