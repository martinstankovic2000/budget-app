package team.devot.budgetapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import team.devot.budgetapp.exception.CustomException;
import team.devot.budgetapp.model.dto.UserDTO;
import team.devot.budgetapp.service.UserService;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    void testRegisterUser() throws Exception {
        UserDTO userDTO = new UserDTO();

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("User registered successfully"));

        verify(userService, times(1)).registerUser(userDTO);
        verifyNoMoreInteractions(userService);
    }

    @Test
    void testLoginUser() throws Exception {
        UserDTO userDTO = new UserDTO();

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Login successful"));

        verify(userService, times(1)).login(userDTO);
        verifyNoMoreInteractions(userService);
    }

    @Test
    void testLogoutUser() throws Exception {
        UserDTO userDTO = new UserDTO();

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/logout")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Logout successful"));

        verify(userService, times(1)).logout(userDTO);
        verifyNoMoreInteractions(userService);
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
