package team.devot.budgetapp.model;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Expense is an entity representing an expense in the application.
 * It is used to persist expense information in the database.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Expense {

    /**
     * The unique identifier of the expense.
     */
    @Id
    @Column(name = "EXPENSE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The description of the expense.
     */
    @Column(name = "EXPENSE_DESCRIPTION")
    private String description;

    /**
     * The amount of the expense.
     */
    @Column(name = "EXPENSE_AMOUNT")
    private Double amount;

    /**
     * The category of the expense.
     */
    @Column(name = "EXPENSE_CATEGORY")
    private String expenseCategory;

    /**
     * The username associated with the expense.
     */
    @Column(name = "EXPENSE_USERNAME")
    private String username;

    /**
     * The date of the expense, which is not nullable.
     */
    @Column(name = "date", nullable = false)
    private LocalDate date;
}