package uz.pdp.pdp_food_delivery.rest.dto.auth;

import lombok.Getter;
import lombok.Setter;
import uz.pdp.pdp_food_delivery.rest.dto.GenericDto;

@Getter
@Setter
public class AuthUserUpdateDto extends GenericDto {


    private String password;

    private String fullName;

    private String phoneNumber;

    private String email;

    private boolean active;

    public AuthUserUpdateDto(String fullName) {
        this.fullName = fullName;
    }
}
