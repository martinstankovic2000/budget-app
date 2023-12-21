package team.devot.budgetapp.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UserDTO is a Data Transfer Object (DTO) representing user information.
 * It is used for communication between the application layers and external systems.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    /**
     * The unique identifier of the user.
     */
    private Long id;

    /**
     * The username of the user.
     */
    private String username;

    /**
     * The password of the user.
     */
    private String password;

    /**
     * The email address of the user.
     */
    private String email;

    /**
     * The balance associated with the user.
     */
    private Double balance;
}
