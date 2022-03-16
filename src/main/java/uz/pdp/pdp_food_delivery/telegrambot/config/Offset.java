package uz.pdp.pdp_food_delivery.telegrambot.config;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class Offset {
    private static final Map<String, Integer> searchOffset = new HashMap<>();

    public Integer getSearchOffset(String chatId) {
        if (Objects.isNull(searchOffset.get(chatId))) {
            setSearchOffset(chatId, 0);
        }
        return searchOffset.get(chatId);
    }

    public void setSearchOffset(String chatId, int offset) {
        if (offset == 0) {
            searchOffset.put(chatId, offset);
        } else searchOffset.put(chatId, getSearchOffset(chatId) + offset);
    }

}
