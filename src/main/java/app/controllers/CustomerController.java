package app.controllers;

import app.entities.*;
import app.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    private final ReservationRepository reservationRepository;
    private final CoworkingSpaceRepository spaceRepository;
    private final UserRepository userRepository;

    @Autowired
    public CustomerController(
            ReservationRepository reservationRepository,
            CoworkingSpaceRepository spaceRepository,
            UserRepository userRepository
    ) {
        this.reservationRepository = reservationRepository;
        this.spaceRepository = spaceRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "customer-dashboard";
    }

    @GetMapping("/spaces")
    public String listSpaces(Model model) {
        model.addAttribute("spaces", spaceRepository.findAll());
        return "customer-spaces";
    }

    @GetMapping("/reservations/{username}")
    public String listReservations(Model model, @PathVariable String username) {
        model.addAttribute("reservations", reservationRepository.findByUsernameIgnoreCase(username));
        return "customer-reservations";
    }

    @GetMapping("/reservations/create")
    public String showCreateReservationForm(Model model) {
        model.addAttribute("reservation", new Reservation());
        model.addAttribute("spaces", spaceRepository.findAll());
        model.addAttribute("users", userRepository.findAll());
        return "customer-create-reservation";
    }

    @PostMapping("/reservations/create")
    public String createReservation(@ModelAttribute Reservation reservation) {
        reservationRepository.save(reservation);
        return "redirect:/customer/reservations";
    }

    @PostMapping("/reservations/cancel/{id}")
    public String cancelReservation(@PathVariable int id) {
        reservationRepository.deleteById(id);
        return "redirect:/customer/reservations";
    }
}
