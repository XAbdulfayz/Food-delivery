package uz.pdp.pdp_food_delivery.telegrambot.config;

import uz.pdp.pdp_food_delivery.rest.dto.feedback.FeedbackCreateDto;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TargetFeedback {
    private static final Map<String, FeedbackCreateDto> targetFeedback = new HashMap<>();

    public static FeedbackCreateDto getTargetFeedback(String chatId) {
        if (Objects.isNull(targetFeedback.get(chatId))) {
            setTargetFeedback(chatId, new FeedbackCreateDto());
        }
        return targetFeedback.get(chatId);
    }

    public static void setTargetFeedback(String chatId, FeedbackCreateDto feedbackCreateDto) {
        targetFeedback.put(chatId, feedbackCreateDto);
    }
}
