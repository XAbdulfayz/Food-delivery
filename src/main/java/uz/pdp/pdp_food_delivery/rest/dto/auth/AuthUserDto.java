package uz.pdp.pdp_food_delivery.rest.dto.auth;

import lombok.Getter;
import lombok.Setter;
import uz.pdp.pdp_food_delivery.rest.dto.GenericDto;
import uz.pdp.pdp_food_delivery.rest.enums.Department;
import uz.pdp.pdp_food_delivery.rest.enums.Role;

@Getter
@Setter
public class AuthUserDto extends GenericDto {

    public String fullName;

    public String phoneNumber;

    public String email;

    public Department department;

    private Role role;

}
