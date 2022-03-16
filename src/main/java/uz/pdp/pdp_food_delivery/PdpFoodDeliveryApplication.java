package uz.pdp.pdp_food_delivery;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;
import uz.pdp.pdp_food_delivery.rest.dto.auth.AuthUserCreateDto;
import uz.pdp.pdp_food_delivery.rest.enums.Department;
import uz.pdp.pdp_food_delivery.rest.enums.Role;
import uz.pdp.pdp_food_delivery.rest.service.auth.AuthUserService;

@SpringBootApplication
@OpenAPIDefinition
@EnableScheduling
public class PdpFoodDeliveryApplication {

    @Autowired
    AuthUserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(PdpFoodDeliveryApplication.class, args);
    }

    //@Bean
    CommandLineRunner run() {
        return args -> {
            userService.create(new AuthUserCreateDto("admin", "+998973130080", "Saydali@gmail.com",
                    passwordEncoder.encode("admin123"), Department.ACADEMIC.name(), Role.ADMIN.name()));
            userService.create(new AuthUserCreateDto("manager", "+998973130081", "Saydali1@gmail.com",
                    passwordEncoder.encode("manager123"), Department.ACADEMIC.name(), Role.ADMIN.name()));
            userService.create(new AuthUserCreateDto("employee", "+998973130082", "Saydali2@gmail.com",
                    passwordEncoder.encode("employee123"), Department.ACADEMIC.name(), Role.ADMIN.name()));
        };
    }
}
