package uz.pdp.pdp_food_delivery.telegrambot.config;

import java.util.HashMap;
import java.util.List;

public class DailyMealName {
    private static HashMap<String, List<String>> dailyMealsNames = new HashMap<>();

    public static List<String> getDailyMealsNames(String chatId) {
        return dailyMealsNames.get(chatId);
    }

    public static void setDailyMealsNames(String chatId, List<String> mealsNames) {
        dailyMealsNames.put(chatId, mealsNames);
    }
}
