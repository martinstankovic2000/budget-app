package team.devot.budgetapp.service;

import org.springframework.data.domain.Page;
import team.devot.budgetapp.model.Expense;
import team.devot.budgetapp.model.Filter;
import team.devot.budgetapp.model.dto.ExpenseDTO;

import java.util.Map;


/**
 * ExpenseService is an interface defining the contract for managing expense-related operations.
 */
public interface ExpenseService {

    /**
     * Retrieves an expense by its unique identifier.
     *
     * @param username The username of the owner of the expense.
     * @param id       The unique identifier of the expense to be retrieved.
     * @return The ExpenseDTO representing the found expense.
     */
    ExpenseDTO getExpense(String username, Long id);

    /**
     * Retrieves a paginated list of expenses based on filtering criteria.
     *
     * @param filter The Filter object containing criteria for filtering expenses.
     * @return A Page of Expense representing the filtered expenses.
     */
    Page<Expense> getAllExpenses(Filter filter);

    /**
     * Creates a new expense.
     *
     * @param expense The ExpenseDTO containing information for the new expense.
     */
    void createExpense(ExpenseDTO expense);

    /**
     * Updates an existing expense.
     *
     * @param expense The ExpenseDTO containing updated information for the expense.
     * @return The updated ExpenseDTO.
     */
    ExpenseDTO updateExpense(ExpenseDTO expense);

    /**
     * Deletes an expense by its unique identifier.
     *
     * @param username The username of the owner of the expense.
     * @param id       The unique identifier of the expense to be deleted.
     */
    void deleteExpense(String username, Long id);

    /**
     * Aggregates expense data based on a specified period.
     *
     * @param username The username of the owner of the expenses.
     * @param period   The period for which data should be aggregated (e.g., "lastMonth").
     * @return A Map containing aggregated data, such as total amounts per expense category.
     */
    Map<String, Double> aggregateDataByPeriod(String username, String period);
}
