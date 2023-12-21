package team.devot.budgetapp.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import team.devot.budgetapp.exception.CustomException;
import team.devot.budgetapp.model.dto.UserDTO;
import team.devot.budgetapp.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testRegisterUser() {
        UserDTO userDTO = new UserDTO(1L,"testUser", "testPassword", "test@example.com", 100.0);

        when(userRepository.existsByUsername(userDTO.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(userDTO.getEmail())).thenReturn(false);

        userService.registerUser(userDTO);

        verify(userRepository, times(1)).save(any());
        assertFalse(userService.getUserLoginStatus().get(userDTO.getUsername()));
    }

    @Test
    void testRegisterUserAlreadyRegistered() {
        UserDTO userDTO = new UserDTO(1L,"existingUser", "testPassword", "test@example.com", 100.0);

        when(userRepository.existsByUsername(userDTO.getUsername())).thenReturn(true);

        assertThrows(CustomException.class, () -> userService.registerUser(userDTO));
        verify(userRepository, times(0)).save(any());
    }

    @Test
    void testRegisterUserEmailAlreadyInUse() {
        UserDTO userDTO = new UserDTO(1L,"newUser", "testPassword", "existing@example.com", 100.0);

        when(userRepository.existsByUsername(userDTO.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(userDTO.getEmail())).thenReturn(true);

        assertThrows(CustomException.class, () -> userService.registerUser(userDTO));
        verify(userRepository, times(0)).save(any());
    }

    @Test
    void testLogin() {
        UserDTO userDTO = new UserDTO(1L,"testUser", "testPassword", "test@example.com", 100.0);

        when(authenticationManager.authenticate(any()))
                .thenReturn(new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword()));

        userService.login(userDTO);

        assertTrue(userService.getUserLoginStatus().get(userDTO.getUsername()));
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void testLoginAlreadyLoggedIn() {
        UserDTO userDTO = new UserDTO(1L,"loggedInUser", "testPassword", "test@example.com", 100.0);
        userService.getUserLoginStatus().put(userDTO.getUsername(), true);

        assertThrows(CustomException.class, () -> userService.login(userDTO));
        verify(authenticationManager, times(0)).authenticate(any());
    }

    @Test
    void testLogout() {
        UserDTO userDTO = new UserDTO(1L,"testUser", "testPassword", "test@example.com", 100.0);
        userService.getUserLoginStatus().put(userDTO.getUsername(), true);

        userService.logout(userDTO);

        assertFalse(userService.getUserLoginStatus().get(userDTO.getUsername()));
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }
}
