package team.devot.budgetapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CustomResponse is a simple class representing a custom response message.
 * It is used to encapsulate response messages in the application.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomResponse {

    /**
     * The message content of the response.
     */
    private String message;
}
