package uz.pdp.pdp_food_delivery.rest.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum FeedbackType {

    COMPLAIN //normmas degani esli cho
    , ADVICE // maslahat ma'nosida
    , THANKS;

    public static FeedbackType getByName(String typ) {
        for (FeedbackType feedbackType : values()) {
            if (feedbackType.name().equalsIgnoreCase(typ)) return feedbackType;
        }
        return ADVICE;
    }

    public static boolean contains(String data) {
        return Arrays.stream(values()).anyMatch(value -> value.name().equalsIgnoreCase(data));
    }

}
