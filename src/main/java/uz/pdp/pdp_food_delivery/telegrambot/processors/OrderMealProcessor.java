package uz.pdp.pdp_food_delivery.telegrambot.processors;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import uz.pdp.pdp_food_delivery.rest.dto.meal.MealDto;
import uz.pdp.pdp_food_delivery.rest.dto.mealorder.MealOrderUpdateDto;
import uz.pdp.pdp_food_delivery.rest.service.dailymeal.DailyMealService;
import uz.pdp.pdp_food_delivery.rest.service.mealorder.MealOrderService;
import uz.pdp.pdp_food_delivery.telegrambot.PdpFoodDeliveryBot;
import uz.pdp.pdp_food_delivery.telegrambot.buttons.InlineBoard;
import uz.pdp.pdp_food_delivery.telegrambot.config.DailyMealName;
import uz.pdp.pdp_food_delivery.telegrambot.config.Offset;
import uz.pdp.pdp_food_delivery.telegrambot.config.OffsetBasedPageRequest;
import uz.pdp.pdp_food_delivery.telegrambot.config.TargetMeal;
import uz.pdp.pdp_food_delivery.telegrambot.enums.MenuState;
import uz.pdp.pdp_food_delivery.telegrambot.enums.SearchState;
import uz.pdp.pdp_food_delivery.telegrambot.states.State;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderMealProcessor {

    private final PdpFoodDeliveryBot bot;
    private final CallbackHandlerProcessor callbackHandlerProcessor;
    private final DailyMealService dailyMealService;
    private final Offset offset;
    private final MealOrderService mealOrderService;

    public void process(Message message) {
        String chatId = message.getChatId().toString();
        offset.setSearchOffset(chatId, 0);
        Pageable pageable = new OffsetBasedPageRequest(offset.getSearchOffset(chatId), State.getLimitState(chatId));
        List<MealDto> meals = dailyMealService.getAllByLimit(pageable);
        List<String> mealsNames = dailyMealService.getAllName();
        DailyMealName.setDailyMealsNames(chatId, mealsNames);

        if (meals.size() == 0) {
//            SendMessage sendMessage = new SendMessage(chatId, LangConfig.get(chatId, "no.meal"));
            SendMessage sendMessage = new SendMessage(chatId, "no meal");
            State.setSearchState(chatId, SearchState.UNDEFINED);
            State.setMenuState(chatId, MenuState.UNDEFINED);
            bot.executeMessage(sendMessage);
        } else {
            SendPhoto sendPhoto = new SendPhoto();
            sendPhoto.setChatId(chatId);
            TargetMeal.setTargetMeal(chatId, meals.get(0));
            sendPhoto.setCaption(callbackHandlerProcessor.getMealMessage(DailyMealName.getDailyMealsNames(chatId), chatId).toString());
            sendPhoto.setReplyMarkup(
                    InlineBoard.dailyMealMenu(meals, State.getLimitState(chatId), offset.getSearchOffset(chatId), chatId));

            sendPhoto.setPhoto(new InputFile(meals.get(0).getPhotoId()));
            bot.executeMessage(sendPhoto);


        }
    }

    public void process(Update message) {
        String chatId = message.getCallbackQuery().getMessage().getChatId().toString();
        offset.setSearchOffset(chatId, 0);
        Pageable pageable = new OffsetBasedPageRequest(offset.getSearchOffset(chatId), State.getLimitState(chatId));
        List<MealDto> meals = dailyMealService.getAllByLimit(pageable);
        List<String> mealsNames = dailyMealService.getAllName();
        DailyMealName.setDailyMealsNames(chatId, mealsNames);

        if (meals.size() == 0) {
//            SendMessage sendMessage = new SendMessage(chatId, LangConfig.get(chatId, "no.meal"));
            SendMessage sendMessage = new SendMessage(chatId, "no meal");
            State.setSearchState(chatId, SearchState.UNDEFINED);
            State.setMenuState(chatId, MenuState.UNDEFINED);
            bot.executeMessage(sendMessage);
        } else {
            SendPhoto sendPhoto = new SendPhoto();
            sendPhoto.setChatId(chatId);
            TargetMeal.setTargetMeal(chatId, meals.get(0));
            sendPhoto.setCaption(callbackHandlerProcessor.getMealMessage(DailyMealName.getDailyMealsNames(chatId), chatId).toString());
            sendPhoto.setReplyMarkup(
                    InlineBoard.dailyMealMenu(meals, State.getLimitState(chatId), offset.getSearchOffset(chatId), chatId));
            sendPhoto.setPhoto(new InputFile(meals.get(0).getPhotoId()));
            bot.executeMessage(sendPhoto);
        }
    }

    public void setOrderMealsByDone(String chatId){
        MealOrderUpdateDto dto= new MealOrderUpdateDto();
        dto.setDone(true);
        dto.setChatId(chatId);
        mealOrderService.update(dto);
    }
}
