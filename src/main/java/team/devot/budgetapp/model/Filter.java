package team.devot.budgetapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Filter is a class representing filtering criteria for querying expenses.
 * It is used to specify parameters for filtering and sorting expenses in the application.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Filter {

    /**
     * The unique identifier filter.
     */
    private Long id;

    /**
     * The description filter.
     */
    private String description;

    /**
     * The minimum amount filter.
     */
    private Double minAmount;

    /**
     * The maximum amount filter.
     */
    private Double maxAmount;

    /**
     * The expense category filter.
     */
    private String expenseCategory;

    /**
     * The username filter.
     */
    private String username;

    /**
     * The start date filter.
     */
    private LocalDate startDate;

    /**
     * The end date filter.
     */
    private LocalDate endDate;

    /**
     * The page number for pagination.
     */
    private int page;

    /**
     * The size of each page for pagination.
     */
    private int size;

    /**
     * The sort order for sorting results (ASC or DESC).
     */
    private String sortOrder;

    /**
     * The field for sorting results.
     */
    private String sortField;
}
