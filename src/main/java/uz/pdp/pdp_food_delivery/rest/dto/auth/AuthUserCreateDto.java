package uz.pdp.pdp_food_delivery.rest.dto.auth;

import lombok.Getter;
import lombok.Setter;
import uz.pdp.pdp_food_delivery.rest.dto.BaseDto;
import uz.pdp.pdp_food_delivery.rest.enums.Department;
import uz.pdp.pdp_food_delivery.rest.enums.Role;

@Getter
@Setter
public class AuthUserCreateDto implements BaseDto {

    public String chatId;

    public AuthUserCreateDto(String chatId) {
        this.chatId = chatId;
    }

    public AuthUserCreateDto(String fullName, String phoneNumber, String email, String password, String department, String role) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.department = department;
        this.role = role;
    }

    private String fullName;

    private String phoneNumber;

    private String email;

    private String password;

    private String department;

    private String role;

}
