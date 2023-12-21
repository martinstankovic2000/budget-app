package team.devot.budgetapp.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CategoryDTO is a Data Transfer Object (DTO) representing category information.
 * It is used for communication between the application layers and external systems.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryDTO {

    /**
     * The unique identifier of the category.
     */
    private Long id;

    /**
     * The name of the category.
     */
    private String name;
}
