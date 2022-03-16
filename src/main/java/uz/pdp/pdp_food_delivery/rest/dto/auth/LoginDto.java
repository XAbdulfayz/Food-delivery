package uz.pdp.pdp_food_delivery.rest.dto.auth;

import lombok.Getter;
import lombok.Setter;

/**
 * @author johnl
 * @since 2/24/2022
 */

@Getter
@Setter
public class LoginDto {
    private String phone;
    private String password;
}
