package ua.bugaienko.instazoo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.bugaienko.instazoo.entity.User;
import ua.bugaienko.instazoo.entity.enums.ERole;
import ua.bugaienko.instazoo.exceptions.UserExistException;
import ua.bugaienko.instazoo.payload.request.SignupRequest;
import ua.bugaienko.instazoo.repository.UsersRepository;

/**
 * @author Sergii Bugaienko
 */

@Service
public class UserService {
    public static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UsersRepository usersRepository, BCryptPasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(SignupRequest userIn) {
        User user = new User();
        user.setEmail(userIn.getEmail());
        user.setName(userIn.getFirstname());
        user.setLastname(userIn.getLastname());
        user.setUsername(userIn.getUsername());
        user.setPassword(passwordEncoder.encode(userIn.getPassword()));
        user.getRoles().add(ERole.ROLE_USER);

        try {
            LOG.info("Saving User {}", userIn.getEmail());
            return usersRepository.save(user);
        } catch (Exception e) {
            LOG.error("Error during registration, {}", e.getMessage());
            throw new UserExistException("The user " + user.getUsername() + "already exist. Please check credentials");
        }

    }

}
