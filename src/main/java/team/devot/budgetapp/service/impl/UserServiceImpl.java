package team.devot.budgetapp.service.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import team.devot.budgetapp.exception.CustomException;
import team.devot.budgetapp.model.User;
import team.devot.budgetapp.model.dto.UserDTO;
import team.devot.budgetapp.repository.UserRepository;
import team.devot.budgetapp.service.UserService;

import java.util.HashMap;
import java.util.Map;

/**
 * UserServiceImpl is an implementation of the UserService interface.
 * It provides business logic for user registration, login and logout.
 */
@Service
@RequiredArgsConstructor
@Getter
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    private final Map<String, Boolean> userLoginStatus = new HashMap<>();

    /**
     * Registers a new user.
     *
     * @param userDTO The UserDTO containing information for the new user.
     * @throws CustomException If the user is already registered, or the email is already in use.
     */
    public void registerUser(UserDTO userDTO) {
        if (userLoginStatus.containsKey(userDTO.getUsername()) && userLoginStatus.get(userDTO.getUsername())) {
            throw new CustomException("User must log out before registering a new account");
        }
        if (userRepository.existsByUsername(userDTO.getUsername()))
            throw new CustomException("User " + userDTO.getUsername() + " is already registered.");
        if (userRepository.existsByEmail(userDTO.getEmail()))
            throw new CustomException("Email " + userDTO.getEmail() + " is already registered.");

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setEmail(userDTO.getEmail());
        user.setBalance(userDTO.getBalance());
        userRepository.save(user);

        userLoginStatus.put(userDTO.getUsername(), false);
    }

    /**
     * Logs in a user.
     *
     * @param userDTO The UserDTO containing the username and password for login.
     * @throws CustomException If the user is already logged in or authentication fails.
     */
    @Override
    public void login(UserDTO userDTO) {
        if (userLoginStatus.containsKey(userDTO.getUsername()) && userLoginStatus.get(userDTO.getUsername())) {
            throw new CustomException("User is already logged in");
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        userLoginStatus.put(userDTO.getUsername(), true);
    }

    /**
     * Logs out a user.
     *
     * @param userDTO The UserDTO containing the username for logout.
     */
    @Override
    public void logout(UserDTO userDTO) {

        userLoginStatus.put(userDTO.getUsername(), false);
        SecurityContextHolder.getContext().setAuthentication(null);
    }
}