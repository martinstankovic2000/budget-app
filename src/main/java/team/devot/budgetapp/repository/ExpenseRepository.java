package team.devot.budgetapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import team.devot.budgetapp.model.Expense;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


/**
 * ExpenseRepository is a Spring Data JPA repository for managing Expense entities.
 * It provides methods for CRUD operations and custom queries related to expenses.
 */
@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    /**
     * Finds an expense by username and id.
     *
     * @param username The username associated with the expense.
     * @param id       The unique identifier of the expense.
     * @return An Optional containing the found expense, or empty if not found.
     */
    Optional<Expense> findByUsernameAndId(String username, Long id);

    /**
     * Filters expenses based on specified criteria.
     *
     * @param username        The username associated with the expenses.
     * @param expenseCategory The category of the expenses (nullable).
     * @param minPrice        The minimum amount of the expenses (nullable).
     * @param maxPrice        The maximum amount of the expenses (nullable).
     * @param startDate       The start date for filtering (nullable).
     * @param endDate         The end date for filtering (nullable).
     * @param pageable        The pagination information.
     * @return A Page of filtered expenses.
     */
    @Query("SELECT e FROM Expense e WHERE " +
            "(:expenseCategory IS NULL OR e.expenseCategory = :expenseCategory) " +
            "AND (:minPrice IS NULL OR e.amount >= :minPrice) " +
            "AND (:maxPrice IS NULL OR e.amount <= :maxPrice) " +
            "AND (:username IS e.username) " +
            "AND (:startDate IS NULL OR e.date >= :startDate) " +
            "AND (:endDate IS NULL OR e.date <= :endDate)")
    Page<Expense> filterExpenses(@Param("username") String username,
                                 @Param("expenseCategory") String expenseCategory,
                                 @Param("minPrice") Double minPrice,
                                 @Param("maxPrice") Double maxPrice,
                                 @Param("startDate") LocalDate startDate,
                                 @Param("endDate") LocalDate endDate,
                                 Pageable pageable);

    /**
     * Aggregates expenses by category within a specified date range.
     *
     * @param username  The username associated with the expenses.
     * @param startDate The start date for aggregation.
     * @param endDate   The end date for aggregation.
     * @return A list of Object arrays representing aggregated data (category, total amount).
     */
    @Query("SELECT e.expenseCategory, SUM(e.amount) FROM Expense e " +
            "WHERE e.date BETWEEN :startDate AND :endDate " +
            "AND (:username IS e.username) " +
            "GROUP BY e.expenseCategory")
    List<Object[]> aggregateExpensesByCategory(@Param("username") String username,
                                               @Param("startDate") LocalDate startDate,
                                               @Param("endDate") LocalDate endDate);
}
