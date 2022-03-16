package uz.pdp.pdp_food_delivery.rest.exceptions;


import org.springframework.http.HttpStatus;

/**
 * @author johnl
 * @since 2/24/2022
 */

public class InvalidTokenException extends RuntimeException {
    private HttpStatus status;

    public InvalidTokenException(String message) {
        this(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public InvalidTokenException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
