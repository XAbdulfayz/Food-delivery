package uz.pdp.pdp_food_delivery.rest.enums;


import lombok.Getter;

@Getter
public enum Department {

    ACADEMIC,
    SALES,
    HR,
    SERVICE,
    MARKETING,
    MENTORS,
    ECMA,
    ACCOUNTING,
    UNICORN,
    HEAD,
    ANONYMOUS;

    public static Department getByName(String dep) {
        for (Department department : values()) {
            if (department.name().equalsIgnoreCase(dep)) return department;
        }
        return ANONYMOUS;
    }

}
