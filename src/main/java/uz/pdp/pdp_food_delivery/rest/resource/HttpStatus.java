package uz.pdp.pdp_food_delivery.rest.resource;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author johnl
 * @since 2/24/2022
 */

@Getter
@RequiredArgsConstructor
public enum HttpStatus {

    INVALID_TOKEN(440, "invalid token"),
    BAD_REQUEST(400, "bad request");

    private final Integer code;
    private final String message;

}
