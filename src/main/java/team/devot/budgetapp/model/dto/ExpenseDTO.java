package team.devot.budgetapp.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * ExpenseDTO is a Data Transfer Object (DTO) representing expense information.
 * It is used for communication between the application layers and external systems.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExpenseDTO {

    /**
     * The unique identifier of the expense.
     */
    private Long id;

    /**
     * The description of the expense.
     */
    private String description;

    /**
     * The amount of the expense.
     */
    private Double amount;

    /**
     * The category of the expense.
     */
    private String expenseCategory;

    /**
     * The username associated with the expense.
     */
    private String username;

    /**
     * The date of the expense.
     */
    private LocalDate date;
}
