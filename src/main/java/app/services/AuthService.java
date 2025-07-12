package app.services;

import app.dao.UserRepository;
import app.entities.User;
import app.utils.CustomExceptions.DuplicateUsernameException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public boolean validateUser(String username, String password, String role) {
        Optional<User> userOpt = userRepository.findByUsernameIgnoreCase(username);
        return userOpt.isPresent()
                && passwordEncoder.matches(password, userOpt.get().getPassword())
                && userOpt.get().getRole().equalsIgnoreCase(role);
    }

    public void createUser(String username, String password) throws DuplicateUsernameException {
        if (userRepository.findByUsernameIgnoreCase(username).isPresent()) {
            throw new DuplicateUsernameException("Username already exists.");
        }
        userRepository.save(new User(username, passwordEncoder.encode(password), "customer"));
    }
}
