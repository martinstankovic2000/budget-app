package team.devot.budgetapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import team.devot.budgetapp.model.Expense;
import team.devot.budgetapp.model.Filter;
import team.devot.budgetapp.model.dto.ExpenseDTO;
import team.devot.budgetapp.service.ExpenseService;

import java.util.Map;

/**
 * Controller class for handling HTTP requests related to expenses.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ExpenseController {

    private final ExpenseService expenseService;

    /**
     * Retrieves an expense by its identifier.
     *
     * @param id The unique identifier of the expense.
     * @return The ExpenseDTO representing the retrieved expense.
     */
    @GetMapping("/expense/{id}")
    public ExpenseDTO getExpense(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return expenseService.getExpense(userDetails.getUsername(), id);
    }

    /**
     * Retrieves all expenses based on the provided filter.
     *
     * @param filter The filter criteria for retrieving expenses.
     * @return A Page containing Expense entities based on the provided filter.
     */
    @PostMapping("/expense/filter")
    public Page<Expense> getAllExpenses(@RequestBody Filter filter) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        filter.setUsername(userDetails.getUsername());
        return expenseService.getAllExpenses(filter);
    }

    /**
     * Creates a new expense.
     *
     * @param expense The ExpenseDTO representing the new expense.
     */
    @PostMapping("/expense")
    public void createExpense(@RequestBody ExpenseDTO expense) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        expense.setUsername(userDetails.getUsername());
        expenseService.createExpense(expense);
    }

    /**
     * Updates an existing expense.
     *
     * @param expense The ExpenseDTO representing the updated expense.
     * @return The ExpenseDTO representing the updated expense.
     */
    @PutMapping("/expense")
    public ExpenseDTO updateExpense(@RequestBody ExpenseDTO expense) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        expense.setUsername(userDetails.getUsername());
        return expenseService.updateExpense(expense);
    }

    /**
     * Deletes an expense by its identifier.
     *
     * @param id The unique identifier of the expense to be deleted.
     */
    @DeleteMapping("/expense/{id}")
    public void deleteExpense(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        expenseService.deleteExpense(userDetails.getUsername(), id);
    }

    /**
     * Aggregates expense data based on a specified period.
     *
     * @param period The period for which expense data should be aggregated.
     * @return A Map containing aggregated expense data.
     */
    @GetMapping("/expense/total")
    public Map<String, Double> aggregateDataByPeriod(@RequestParam String period) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return expenseService.aggregateDataByPeriod(userDetails.getUsername(), period);
    }
}
