package uz.pdp.pdp_food_delivery.telegrambot.processors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import uz.pdp.pdp_food_delivery.rest.dto.meal.MealDto;
import uz.pdp.pdp_food_delivery.rest.service.meal.MealService;
import uz.pdp.pdp_food_delivery.telegrambot.LangConfig;
import uz.pdp.pdp_food_delivery.telegrambot.PdpFoodDeliveryBot;
import uz.pdp.pdp_food_delivery.telegrambot.buttons.InlineBoard;
import uz.pdp.pdp_food_delivery.telegrambot.enums.MenuState;
import uz.pdp.pdp_food_delivery.telegrambot.states.State;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DailyMealProcessor {

    private final CallbackHandlerProcessor callbackHandlerProcessor;
    private final MealService mealService;
    private final PdpFoodDeliveryBot bot;

    public void process(Message message) {
        String chatId = message.getChatId().toString();
        String text = message.getText();
        List<MealDto> meals = mealService.getAll();
        SendMessage sendMessage;
        if (meals.size() == 0) {
            sendMessage = new SendMessage(chatId, LangConfig.get(chatId, "no.book.name"));
            State.setMenuState(chatId, MenuState.UNDEFINED);
        } else {
            sendMessage = new SendMessage(chatId, callbackHandlerProcessor.getDailyMealMessage(meals, chatId).toString());
            sendMessage.setReplyMarkup(InlineBoard.meal(meals));
        }
        bot.executeMessage(sendMessage);
    }
}
