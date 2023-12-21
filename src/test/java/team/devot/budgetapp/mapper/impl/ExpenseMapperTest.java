package team.devot.budgetapp.mapper.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import team.devot.budgetapp.model.Expense;
import team.devot.budgetapp.model.dto.ExpenseDTO;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ExpenseMapperTest {

    @InjectMocks
    private ExpenseMapper expenseMapper;

    @Test
    void testMapTo() {
        Expense expense = Expense.builder()
                .id(1L)
                .description("Test Expense")
                .amount(50.00)
                .expenseCategory("Test Category")
                .date(LocalDate.now())
                .build();

        ExpenseDTO expenseDTO = expenseMapper.mapTo(expense);

        assertEquals(1L, expenseDTO.getId());
        assertEquals("Test Expense", expenseDTO.getDescription());
        assertEquals(50.00, expenseDTO.getAmount());
        assertEquals("Test Category", expenseDTO.getExpenseCategory());
        assertEquals(LocalDate.now(), expenseDTO.getDate());
    }

    @Test
    void testMapFrom() {
        ExpenseDTO expenseDTO = ExpenseDTO.builder()
                .id(1L)
                .description("Test Expense")
                .amount(50.00)
                .expenseCategory("Test Category")
                .date(LocalDate.now())
                .username("Test User")
                .build();

        Expense expense = expenseMapper.mapFrom(expenseDTO);

        assertEquals(1L, expense.getId());
        assertEquals("Test Expense", expense.getDescription());
        assertEquals(50.00, expense.getAmount());
        assertEquals("Test Category", expense.getExpenseCategory());
        assertEquals(LocalDate.now(), expense.getDate());
        assertEquals("Test User", expense.getUsername());
    }
}
