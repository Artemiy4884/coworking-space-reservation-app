package app.controllers;

import app.entities.CoworkingSpace;
import app.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ReservationRepository reservationRepository;
    private final CoworkingSpaceRepository spaceRepository;
    private final UserRepository userRepository;

    @Autowired
    public AdminController(
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
        return "admin-dashboard";
    }

    @GetMapping("/reservations")
    public String listReservations(Model model) {
        model.addAttribute("reservations", reservationRepository.findAll());
        return "admin-reservations";
    }

    @PostMapping("/reservations/delete/{id}")
    public String deleteReservation(@PathVariable int id) {
        reservationRepository.deleteById(id);
        return "redirect:/admin/reservations";
    }

    @GetMapping("/spaces")
    public String listSpaces(Model model) {
        model.addAttribute("spaces", spaceRepository.findAll());
        return "admin-spaces";
    }

    @GetMapping("/spaces/create")
    public String showCreateSpaceForm(Model model) {
        model.addAttribute("space", new CoworkingSpace());
        return "admin-create-space";
    }

    @PostMapping("/spaces/create")
    public String createSpace(@ModelAttribute CoworkingSpace space) {
        spaceRepository.save(space);
        return "redirect:/admin/spaces";
    }

    @PostMapping("/spaces/delete/{id}")
    public String deleteSpace(@PathVariable int id) {
        spaceRepository.deleteById(id);
        return "redirect:/admin/spaces";
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "admin-users";
    }
}
