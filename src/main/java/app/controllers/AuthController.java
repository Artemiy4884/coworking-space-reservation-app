package app.controllers;

import app.services.AuthService;
import app.utils.CustomExceptions.DuplicateUsernameException;
import app.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final JWTUtil jwtUtil;

    @Autowired
    public AuthController(AuthService authService, JWTUtil jwtUtil) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username,
                                        @RequestParam String password,
                                        @RequestParam String role) {
        boolean valid = authService.validateUser(username, password, role);
        if (valid) {
            return ResponseEntity.ok(jwtUtil.generateToken(username, role));
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
