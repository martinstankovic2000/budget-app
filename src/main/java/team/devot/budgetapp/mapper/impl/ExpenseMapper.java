package team.devot.budgetapp.mapper.impl;

import org.springframework.stereotype.Component;
import team.devot.budgetapp.mapper.Mapper;
import team.devot.budgetapp.model.Expense;
import team.devot.budgetapp.model.dto.ExpenseDTO;

import java.time.LocalDate;

/**
 * ExpenseMapper is a component responsible for mapping between Expense entities and ExpenseDTOs.
 * It implements the Mapper interface for bidirectional mapping.
 */
@Component
public class ExpenseMapper implements Mapper<Expense, ExpenseDTO> {


    /**
     * Maps an Expense entity to an ExpenseDTO.
     *
     * @param expense The Expense entity to be mapped.
     * @return The corresponding ExpenseDTO.
     */
    @Override
    public ExpenseDTO mapTo(Expense expense) {
        return ExpenseDTO.builder()
                .id(expense.getId())
                .description(expense.getDescription())
                .amount(expense.getAmount())
                .expenseCategory(expense.getExpenseCategory())
                .date(expense.getDate())
                .build();
    }

    /**
     * Maps an ExpenseDTO to an Expense entity.
     *
     * @param expenseDTO The ExpenseDTO to be mapped.
     * @return The corresponding Expense entity.
     */
    @Override
    public Expense mapFrom(ExpenseDTO expenseDTO) {
        return Expense.builder()
                .id(expenseDTO.getId())
                .description(expenseDTO.getDescription())
                .amount(expenseDTO.getAmount())
                .expenseCategory(expenseDTO.getExpenseCategory())
                .username(expenseDTO.getUsername())
                .date(LocalDate.now())
                .build();
    }
}
