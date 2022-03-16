package uz.pdp.pdp_food_delivery.telegrambot.config;

import org.springframework.stereotype.Component;
import uz.pdp.pdp_food_delivery.rest.dto.meal.MealDto;

import java.util.HashMap;
import java.util.Map;

@Component
public class TargetMeal {
    private static final Map<String, String> mealId = new HashMap<>();
    private static final Map<String, MealDto> targetMeal = new HashMap<>();



    public static String getMealId(String chatId) {
        return mealId.get(chatId);
    }

    public static void setMealId(String chatId, String photoId) {
        mealId.put(chatId, photoId);
    }

    public static MealDto getTargetMeal(String chatId) {
        return targetMeal.get(chatId);
    }

    public static void setTargetMeal(String chatId, MealDto mealDto) {
        targetMeal.put(chatId, mealDto);
    }
}
