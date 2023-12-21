package team.devot.budgetapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.devot.budgetapp.model.CustomResponse;
import team.devot.budgetapp.model.dto.UserDTO;
import team.devot.budgetapp.service.UserService;

/**
 * Controller class for handling HTTP requests related to user registration and authentication.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    /**
     * Registers a new user in the system.
     *
     * @param userDTO The UserDTO representing the user's registration information.
     * @return A ResponseEntity with a CustomResponse indicating the result of the registration.
     */
    @PostMapping("/register")
    public ResponseEntity<CustomResponse> registerUser(@RequestBody UserDTO userDTO) {
        userService.registerUser(userDTO);
        return ResponseEntity.ok(new CustomResponse("User registered successfully"));
    }

    /**
     * Logs in a user.
     *
     * @param userDTO The UserDTO representing the user's login information.
     * @return A ResponseEntity with a CustomResponse indicating the result of the login.
     */
    @PostMapping("/login")
    public ResponseEntity<CustomResponse> loginUser(@RequestBody UserDTO userDTO) {
        userService.login(userDTO);
        return ResponseEntity.ok(new CustomResponse("Login successful"));
    }

    /**
     * Logs out a user.
     *
     * @param userDTO The UserDTO representing the user's logout information.
     * @return A ResponseEntity with a CustomResponse indicating the result of the logout.
     */
    @PostMapping("/logout")
    public ResponseEntity<CustomResponse> logoutUser(@RequestBody UserDTO userDTO) {
        userService.logout(userDTO);
        return ResponseEntity.ok(new CustomResponse("Logout successful"));
    }

}
