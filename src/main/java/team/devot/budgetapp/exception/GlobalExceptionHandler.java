package team.devot.budgetapp.exception;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import team.devot.budgetapp.model.CustomResponse;

/**
 * GlobalExceptionHandler is a centralized exception handler for handling specific exceptions globally.
 * It provides methods to handle various types of exceptions and customize the HTTP response accordingly.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles EntityNotFoundException and returns a ResponseEntity with a custom error message.
     *
     * @param exception The EntityNotFoundException that occurred.
     * @return A ResponseEntity with a CustomResponse and HTTP status code 404 (Not Found).
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<CustomResponse> handleEntityNotFoundException(EntityNotFoundException exception) {
        CustomResponse customError = new CustomResponse(exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(customError);
    }

    /**
     * Handles AuthenticationException and returns a ResponseEntity with a custom error message.
     *
     * @param ex The AuthenticationException that occurred.
     * @return A ResponseEntity with a CustomResponse and HTTP status code 401 (Unauthorized).
     */
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<CustomResponse> handleAuthenticationException(AuthenticationException ex) {
        CustomResponse customError = new CustomResponse("Authentication failed: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(customError);
    }

    /**
     * Handles CustomException and returns a ResponseEntity with a custom error message.
     *
     * @param ex The CustomException that occurred.
     * @return A ResponseEntity with a CustomResponse and HTTP status code 412 (Precondition Failed).
     */
    @ExceptionHandler(CustomException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    public ResponseEntity<CustomResponse> handleCustomUserExceptionException(CustomException ex) {
        CustomResponse customError = new CustomResponse(ex.getMessage());
        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(customError);
    }

}
