package app.controllers;

import app.entities.CoworkingSpace;
import app.entities.Reservation;
import app.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/spaces")
    public CoworkingSpace addSpace(@RequestParam String type,
                                   @RequestParam String details,
                                   @RequestParam BigDecimal price) {
        return adminService.addSpace(type, details, price);
    }

    @DeleteMapping("/spaces/delete/{id}")
    public String removeSpace(@PathVariable Integer id) {
        boolean removed = adminService.removeSpace(id);
        return removed ? "Space removed" : "Space not found";
    }

    @GetMapping("/reservations")
    public List<Reservation> getReservations() {
        return adminService.getReservations();
    }
}
