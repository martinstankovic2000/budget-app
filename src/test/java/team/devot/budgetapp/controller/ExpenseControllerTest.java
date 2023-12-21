package team.devot.budgetapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import team.devot.budgetapp.model.Expense;
import team.devot.budgetapp.model.Filter;
import team.devot.budgetapp.model.dto.ExpenseDTO;
import team.devot.budgetapp.service.ExpenseService;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExpenseControllerTest {

    @Mock
    private ExpenseService expenseService;

    @InjectMocks
    private ExpenseController expenseController;

    @Test
    void testGetExpense() throws Exception {
        long expenseId = 1L;
        ExpenseDTO expenseDTO = new ExpenseDTO();
        expenseDTO.setUsername("test");

        Authentication authentication = mock(Authentication.class);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = new User("username", "password", Collections.emptyList());
        when(authentication.getPrincipal()).thenReturn(userDetails);

        when(expenseService.getExpense(anyString(), eq(expenseId))).thenReturn(expenseDTO);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(expenseController).build();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/expense/{id}", expenseId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("test"));

        verify(expenseService, times(1)).getExpense(anyString(), eq(expenseId));
        verifyNoMoreInteractions(expenseService);
    }

    @Test
    void testGetAllExpenses() throws Exception {
        Filter filter = new Filter();
        Page<Expense> expensePage = new PageImpl<>(Arrays.asList(new Expense(), new Expense()));

        Authentication authentication = mock(Authentication.class);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = new User("username", "password", Collections.emptyList());
        when(authentication.getPrincipal()).thenReturn(userDetails);

        when(expenseService.getAllExpenses(any())).thenReturn(expensePage);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(expenseController).build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/expense/filter")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(filter)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.length()").value(expensePage.getContent().size()));

        verify(expenseService, times(1)).getAllExpenses(any());
        verifyNoMoreInteractions(expenseService);
    }

    @Test
    void testCreateExpense() throws Exception {
        ExpenseDTO expenseDTO = new ExpenseDTO();
        expenseDTO.setUsername("username");

        Authentication authentication = mock(Authentication.class);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = new User("username", "password", Collections.emptyList());
        when(authentication.getPrincipal()).thenReturn(userDetails);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(expenseController).build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/expense")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(expenseDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(expenseService, times(1)).createExpense(expenseDTO);
        verifyNoMoreInteractions(expenseService);
    }

    @Test
    void testUpdateExpense() throws Exception {
        ExpenseDTO expenseDTO = new ExpenseDTO();
        expenseDTO.setUsername("username");

        Authentication authentication = mock(Authentication.class);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = new User("username", "password", Collections.emptyList());
        when(authentication.getPrincipal()).thenReturn(userDetails);

        when(expenseService.updateExpense(expenseDTO)).thenReturn(expenseDTO);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(expenseController).build();

        mockMvc.perform(MockMvcRequestBuilders.put("/api/expense")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(expenseDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("username"));

        verify(expenseService, times(1)).updateExpense(expenseDTO);
        verifyNoMoreInteractions(expenseService);
    }

    @Test
    void testDeleteExpense() throws Exception {
        long expenseId = 1L;

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(expenseController).build();

        Authentication authentication = mock(Authentication.class);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = new User("username", "password", Collections.emptyList());
        when(authentication.getPrincipal()).thenReturn(userDetails);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/expense/{id}", expenseId))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(expenseService, times(1)).deleteExpense(anyString(), eq(expenseId));
        verifyNoMoreInteractions(expenseService);
    }

    @Test
    void testAggregateDataByPeriod() throws Exception {
        String period = "monthly";
        Map<String, Double> aggregatedData = Collections.singletonMap("key", 123.45);

        Authentication authentication = mock(Authentication.class);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = new User("username", "password", Collections.emptyList());
        when(authentication.getPrincipal()).thenReturn(userDetails);

        when(expenseService.aggregateDataByPeriod(anyString(), eq(period))).thenReturn(aggregatedData);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(expenseController).build();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/expense/total")
                .param("period", period))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.key").value(123.45));

        verify(expenseService, times(1)).aggregateDataByPeriod(anyString(), eq(period));
        verifyNoMoreInteractions(expenseService);
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
