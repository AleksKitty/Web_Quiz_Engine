package engine.presentation;

import engine.businesslayer.User;
import engine.persistance.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class RegistrationController {
    @Autowired
    UserRepository userRepo;
    @Autowired
    PasswordEncoder encoder;

    @PostMapping("/api/register")
    public void register(@RequestBody User user) {
        if (user.getPassword().length() < 5 || !user.getEmail().contains("@") || !user.getEmail().contains(".")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // email already taken
        if (userRepo.findUserByEmail(user.getEmail()) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        user.setRole("ROLE_USER");
        user.setPassword(encoder.encode(user.getPassword()));

        userRepo.save(user);
    }
}
