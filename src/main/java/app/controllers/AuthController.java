package app.controllers;

import app.services.AuthService;
import app.utils.CustomExceptions.DuplicateUsernameException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username,
                                        @RequestParam String password,
                                        @RequestParam String role) {
        boolean valid = authService.validateUser(username, password, role);
        if (valid) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestParam String username,
                                         @RequestParam String password) {
        try {
            authService.createUser(username, password);
            return ResponseEntity.ok("Account created");
        } catch (DuplicateUsernameException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
