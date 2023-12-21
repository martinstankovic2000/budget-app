package team.devot.budgetapp.service.impl;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import team.devot.budgetapp.exception.CustomException;
import team.devot.budgetapp.mapper.impl.ExpenseMapper;
import team.devot.budgetapp.model.Category;
import team.devot.budgetapp.model.Expense;
import team.devot.budgetapp.model.Filter;
import team.devot.budgetapp.model.User;
import team.devot.budgetapp.model.dto.ExpenseDTO;
import team.devot.budgetapp.repository.CategoryRepository;
import team.devot.budgetapp.repository.ExpenseRepository;
import team.devot.budgetapp.repository.UserRepository;
import team.devot.budgetapp.service.CategoryService;
import team.devot.budgetapp.service.ExpenseService;

import java.time.Instant;
import java.time.LocalDate;
import java.util.*;

/**
 * ExpenseServiceImpl is an implementation of the ExpenseService interface.
 * It provides business logic for managing expenses in the application.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final ExpenseMapper mapper;

    /**
     * Retrieves an expense by username and ID.
     *
     * @param username The username associated with the expense.
     * @param id       The unique identifier of the expense to be retrieved.
     * @return The ExpenseDTO representing the found expense.
     * @throws EntityNotFoundException If the expense with the given ID is not found.
     */
    @Override
    public ExpenseDTO getExpense(String username, Long id) {
        return mapper.mapTo(expenseRepository.findByUsernameAndId(username, id)
                .orElseThrow(() -> new EntityNotFoundException("Expense with ID " + id + " was not found!")));
    }

    /**
     * Retrieves expenses based on filtering criteria.
     *
     * @param filter The Filter object containing criteria for filtering and pagination.
     * @return A Page of Expense entities representing filtered expenses.
     */
    @Override
    public Page<Expense> getAllExpenses(Filter filter) {
        Pageable pageable = PageRequest.of(filter.getPage(), filter.getSize(),
                Sort.by(Sort.Direction.fromString(filter.getSortOrder()), filter.getSortField()));
        return expenseRepository.filterExpenses(filter.getUsername(), filter.getExpenseCategory(),
                filter.getMinAmount(), filter.getMaxAmount(), filter.getStartDate(), filter.getEndDate(), pageable);
    }

    /**
     * Creates a new expense.
     *
     * @param expense The ExpenseDTO containing information for the new expense.
     */
    @Override
    public void createExpense(ExpenseDTO expense) {
        User user = userRepository.findByUsername(expense.getUsername()).orElseThrow(() -> new EntityNotFoundException("User with username " + expense.getUsername() + " was not found!"));
        if (user.getBalance() >= expense.getAmount()) {
            user.setBalance(user.getBalance()-expense.getAmount());
            expenseRepository.save(mapper.mapFrom(expense));
        }
        else
            throw new CustomException("Insufficient balance! Current balance: " + user.getBalance());
    }

    /**
     * Updates an existing expense.
     *
     * @param expense The ExpenseDTO containing updated information for the expense.
     * @return The updated ExpenseDTO.
     * @throws EntityNotFoundException If the expense with the given ID is not found.
     */
    @Override
    public ExpenseDTO updateExpense(ExpenseDTO expense) {
        getExpense(expense.getUsername(), expense.getId());
        User user = userRepository.findByUsername(expense.getUsername()).orElseThrow(() -> new EntityNotFoundException("User with username " + expense.getUsername() + " was not found!"));
        if (user.getBalance() >= expense.getAmount()) {
            user.setBalance(user.getBalance()-expense.getAmount());
            return mapper.mapTo(expenseRepository.save(mapper.mapFrom(expense)));
        }
        else
            throw new CustomException("Insufficient balance! Current balance: " + user.getBalance());
    }

    /**
     * Deletes an expense by username and ID.
     *
     * @param username The username associated with the expense.
     * @param id       The unique identifier of the expense to be deleted.
     * @throws EntityNotFoundException If the expense with the given ID is not found.
     */
    @Override
    public void deleteExpense(String username, Long id) {
        ExpenseDTO expense = getExpense(username, id);
        User user = userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User with username " + expense.getUsername() + " was not found!"));
        user.setBalance(user.getBalance()+expense.getAmount());
        expenseRepository.deleteById(id);
    }

    /**
     * Aggregates expense data by category for a specified period.
     *
     * @param username The username associated with the expenses.
     * @param period   The period for which data should be aggregated (e.g., "lastMonth").
     * @return A map containing aggregated data by category.
     * @throws CustomException If the specified period is invalid.
     */
    @Override
    public Map<String, Double> aggregateDataByPeriod(String username, String period) {
        LocalDate startDate;
        LocalDate endDate = LocalDate.now();

        switch (period) {
            case "lastMonth" -> startDate = endDate.minusMonths(1).withDayOfMonth(1);
            case "lastQuarter" -> startDate = endDate.minusMonths(3).withDayOfMonth(1);
            case "lastYear" -> startDate = endDate.minusYears(1).withDayOfYear(1);
            default -> throw new CustomException("Invalid period: " + period);
        }
        return performDataAggregation(username, startDate, endDate);
    }

    private Map<String, Double> performDataAggregation(String username, LocalDate startDate, LocalDate endDate) {

        List<Object[]> result = expenseRepository.aggregateExpensesByCategory(username, startDate, endDate);
        Map<String, Double> aggregatedData = new HashMap<>();

        for (Object[] row : result) {
            String category = (String) row[0];
            Double totalAmount = (Double) row[1];
            aggregatedData.put(category, totalAmount);
        }

        return aggregatedData;
    }
}
