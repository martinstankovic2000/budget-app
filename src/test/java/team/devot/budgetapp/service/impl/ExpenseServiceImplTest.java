package team.devot.budgetapp.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import team.devot.budgetapp.exception.CustomException;
import team.devot.budgetapp.mapper.impl.ExpenseMapper;
import team.devot.budgetapp.model.Expense;
import team.devot.budgetapp.model.Filter;
import team.devot.budgetapp.model.User;
import team.devot.budgetapp.model.dto.ExpenseDTO;
import team.devot.budgetapp.repository.ExpenseRepository;
import team.devot.budgetapp.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExpenseServiceImplTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ExpenseMapper expenseMapper;

    @InjectMocks
    private ExpenseServiceImpl expenseService;

    @Test
    void testGetExpense() {
        String username = "testUser";
        Long expenseId = 1L;
        Expense expense = new Expense();
        expense.setId(expenseId);
        when(expenseRepository.findByUsernameAndId(username, expenseId)).thenReturn(Optional.of(expense));
        when(expenseMapper.mapTo(expense)).thenReturn(new ExpenseDTO(expenseId, "Test Expense", 100.0, "Category", "username",LocalDate.now()));

        ExpenseDTO result = expenseService.getExpense(username, expenseId);

        assertNotNull(result);
        assertEquals(expenseId, result.getId());
        verify(expenseRepository, times(1)).findByUsernameAndId(username, expenseId);
    }

    @Test
    void testGetExpenseNotFound() {
        String username = "testUser";
        Long expenseId = 1L;
        when(expenseRepository.findByUsernameAndId(username, expenseId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> expenseService.getExpense(username, expenseId));
        verify(expenseRepository, times(1)).findByUsernameAndId(username, expenseId);
    }

    @Test
    void testGetAllExpenses() {
        Filter filter = new Filter(null,null,null,null,null,null,null,null, 0, 10, "asc", "id");
        List<Expense> expenses = Arrays.asList(
                new Expense(1L, "Expense 1", 50.0, "Category 1", "username",LocalDate.now()),
                new Expense(2L, "Expense 2", 75.0, "Category 2", "username",LocalDate.now())
        );
        when(expenseRepository.filterExpenses(any(),any(),any(),any(),any(),any(),any()))
                .thenReturn(new PageImpl<>(expenses));

        assertNotNull(expenseService.getAllExpenses(filter));
    }

    @Test
    void testCreateExpense() {
        ExpenseDTO expenseDTO = new ExpenseDTO(null, "New Expense", 100.0, "Category", "username",LocalDate.now());
        User user = new User();
        user.setBalance(150.0);
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));
        assertDoesNotThrow(() -> expenseService.createExpense(expenseDTO));

        verify(expenseRepository, times(1)).save(any());
    }

    @Test
    void testUpdateExpense() {
        ExpenseDTO expenseDTO = new ExpenseDTO(1L, "Updated Expense", 150.0, "Updated Category", "username",LocalDate.now());
        when(expenseRepository.findByUsernameAndId(expenseDTO.getUsername(), expenseDTO.getId())).thenReturn(Optional.of(new Expense()));
        when(expenseRepository.save(any())).thenReturn(new Expense());
        when(expenseMapper.mapTo(any(Expense.class))).thenReturn(expenseDTO);
        User user = new User();
        user.setBalance(150.0);
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));
        ExpenseDTO result = expenseService.updateExpense(expenseDTO);

        assertNotNull(result);
        verify(expenseRepository, times(1)).findByUsernameAndId(expenseDTO.getUsername(), expenseDTO.getId());
        verify(expenseRepository, times(1)).save(any());
    }

    @Test
    void testUpdateExpenseNotFound() {
        ExpenseDTO expenseDTO = new ExpenseDTO(1L, "Updated Expense", 150.0, "Updated Category", "username",LocalDate.now());
        when(expenseRepository.findByUsernameAndId(expenseDTO.getUsername(), expenseDTO.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> expenseService.updateExpense(expenseDTO));
        verify(expenseRepository, times(1)).findByUsernameAndId(expenseDTO.getUsername(), expenseDTO.getId());
        verify(expenseRepository, times(0)).save(any());
    }

    @Test
    void testDeleteExpense() {
        String username = "testUser";
        Long expenseId = 1L;
        Expense expense = new Expense(1L, "Test", 100.0, "Test", "testUser", LocalDate.now());
        when(expenseRepository.findByUsernameAndId(username, expenseId)).thenReturn(Optional.of(expense));
        when(expenseService.getExpense(username, expenseId)).thenReturn(new ExpenseDTO(1L,"Test", 100.0, "Test", "testUser", LocalDate.now()));
        User user = new User();
        user.setBalance(150.0);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        assertDoesNotThrow(() -> expenseService.deleteExpense(username, expenseId));

        verify(expenseRepository, times(2)).findByUsernameAndId(username, expenseId);
        verify(expenseRepository, times(1)).deleteById(expenseId);
    }

    @Test
    void testDeleteExpenseNotFound() {
        String username = "testUser";
        Long expenseId = 1L;
        when(expenseRepository.findByUsernameAndId(username, expenseId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> expenseService.deleteExpense(username, expenseId));
        verify(expenseRepository, times(1)).findByUsernameAndId(username, expenseId);
        verify(expenseRepository, times(0)).deleteById(expenseId);
    }

    @Test
    void testAggregateDataByPeriod() {
        String username = "testUser";
        String period = "lastMonth";
        LocalDate startDate = LocalDate.now().minusMonths(1).withDayOfMonth(1);
        LocalDate endDate = LocalDate.now();
        when(expenseRepository.aggregateExpensesByCategory(username, startDate, endDate)).thenReturn(Collections.singletonList(new Object[]{"Category", 100.0}));

        Map<String, Double> result = expenseService.aggregateDataByPeriod(username, period);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.containsKey("Category"));
        assertEquals(100.0, result.get("Category"));
        verify(expenseRepository, times(1)).aggregateExpensesByCategory(username, startDate, endDate);
    }

    @Test
    void testAggregateDataByPeriodInvalidPeriod() {
        String username = "testUser";
        String period = "invalidPeriod";

        assertThrows(CustomException.class, () -> expenseService.aggregateDataByPeriod(username, period));
        verify(expenseRepository, times(0)).aggregateExpensesByCategory(any(), any(), any());
    }
}
