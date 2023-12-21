package team.devot.budgetapp.exception;

/**
 * CustomException is a runtime exception that can be used to represent custom application-specific errors.
 * It extends the RuntimeException class.
 */
public class CustomException extends RuntimeException {

    /**
     * Constructs a new CustomException with the specified error message.
     *
     * @param message The error message associated with the exception.
     */
    public CustomException(String message) {
        super(message);
    }
}
