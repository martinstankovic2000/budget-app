package team.devot.budgetapp.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import team.devot.budgetapp.model.Expense;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class ExpenseRepositoryTest {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Test
    void testFindByUsernameAndId() {
        Expense expense = new Expense();
        expense.setUsername("testUser");
        expense.setDescription("Test Expense");
        expense.setExpenseCategory("Category1");
        expense.setAmount(100.0);
        expense.setDate(LocalDate.now());
        expenseRepository.save(expense);

        Optional<Expense> foundExpense = expenseRepository.findByUsernameAndId("testUser", expense.getId());

        assertTrue(foundExpense.isPresent());
        assertEquals("Test Expense", foundExpense.get().getDescription());
    }

    @Test
    void testFilterExpenses() {
        Expense expense = new Expense();
        expense.setUsername("testUser");
        expense.setExpenseCategory("Category1");
        expense.setAmount(100.0);
        expense.setDate(LocalDate.now());
        expenseRepository.save(expense);

        Expense expense2 = new Expense();
        expense2.setUsername("testUser");
        expense2.setExpenseCategory("Category2");
        expense2.setAmount(150.0);
        expense2.setDate(LocalDate.now());
        expenseRepository.save(expense2);

        Page<Expense> filteredExpenses = expenseRepository.filterExpenses("testUser", "Category1", 50.0, 200.0,
                null, null, PageRequest.of(0, 10));

        assertEquals(1, filteredExpenses.getTotalElements());
        assertEquals("Category1", filteredExpenses.getContent().get(0).getExpenseCategory());
    }

    @Test
    void testAggregateExpensesByCategory() {
        Expense expense = new Expense();
        expense.setUsername("testUser");
        expense.setExpenseCategory("Category1");
        expense.setAmount(100.0);
        expense.setDate(LocalDate.now());
        expenseRepository.save(expense);

        Expense expense2 = new Expense();
        expense2.setUsername("testUser");
        expense2.setExpenseCategory("Category2");
        expense2.setAmount(150.0);
        expense2.setDate(LocalDate.now());
        expenseRepository.save(expense2);

        List<Object[]> aggregatedData = expenseRepository.aggregateExpensesByCategory("testUser",
                LocalDate.now().minusDays(1), LocalDate.now());

        assertEquals(2, aggregatedData.size());
        assertEquals("Category1", aggregatedData.get(0)[0]);
        assertEquals(100.0, aggregatedData.get(0)[1]);
        assertEquals("Category2", aggregatedData.get(1)[0]);
        assertEquals(150.0, aggregatedData.get(1)[1]);
    }
}
