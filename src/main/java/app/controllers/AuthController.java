package app.controllers;

import app.auth.TokenBlacklist;
import app.dto.UserDTO;
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
    private final TokenBlacklist tokenBlacklist;

    @Autowired
    public AuthController(AuthService authService, JWTUtil jwtUtil, TokenBlacklist tokenBlacklist) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
        this.tokenBlacklist = tokenBlacklist;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDTO userDTO) {
        boolean valid = authService.validateUser(
                userDTO.getUsername(),
                userDTO.getPassword(),
                userDTO.getRole());
        if (valid) {
            return ResponseEntity.ok(jwtUtil.generateToken(userDTO.getUsername(), userDTO.getRole()));
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

    @PostMapping("/auth/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);
            String jti = jwtUtil.extractClaims(jwt).getId();
            tokenBlacklist.blacklist(jti);
        }
        return ResponseEntity.ok().build();
    }

}
