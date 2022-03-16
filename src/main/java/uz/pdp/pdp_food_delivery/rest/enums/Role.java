package uz.pdp.pdp_food_delivery.rest.enums;

import lombok.Getter;

@Getter
public enum Role {
    SUPER_ADMIN,
    ADMIN,
    DEPARTMENT_HEAD,
    FOOD_MANAGER,
    USER,
    ANONYMOUS;

    public static Role getByName(String rol) {
        for (Role role : values()) {
            if (role.name().equalsIgnoreCase(rol)) return role;
        }
        return ANONYMOUS;
    }
}