package uz.pdp.pdp_food_delivery.telegrambot.processors;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageMedia;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import uz.pdp.pdp_food_delivery.rest.dto.meal.MealDto;
import uz.pdp.pdp_food_delivery.rest.service.dailymeal.DailyMealService;
import uz.pdp.pdp_food_delivery.telegrambot.LangConfig;
import uz.pdp.pdp_food_delivery.telegrambot.PdpFoodDeliveryBot;
import uz.pdp.pdp_food_delivery.telegrambot.buttons.InlineBoard;
import uz.pdp.pdp_food_delivery.telegrambot.config.DailyMealName;
import uz.pdp.pdp_food_delivery.telegrambot.config.Offset;
import uz.pdp.pdp_food_delivery.telegrambot.config.OffsetBasedPageRequest;
import uz.pdp.pdp_food_delivery.telegrambot.config.TargetMeal;
import uz.pdp.pdp_food_delivery.telegrambot.states.State;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CallbackHandlerProcessor {

    private final PdpFoodDeliveryBot bot;
    private final DailyMealService dailyMealService;
    private final Offset offset;

    public StringBuilder getDailyMealMessage(List<MealDto> meals, String chatId) {
        StringBuilder messageText = new StringBuilder();
        int i = 1;
        if (meals.size() == 0) {
            messageText.append(LangConfig.get(chatId, "no.book"));
        } else {
            for (MealDto meal : meals) {
                messageText.append("<code>")
                        .append(i++).append(".</code> ")
                        .append(" <b>").append(meal.getName()).append("</b>\n");
            }
        }
        return messageText;
    }

    public StringBuilder getMealMessage(List<String> mealNames, String chatId) {
        StringBuilder messageText = new StringBuilder();
        int i = 1;
        if (mealNames.size() == 0) {
            messageText.append(LangConfig.get(chatId, "no.meal"));
        } else {
            for (String meal : mealNames) {
                messageText.append("<code>").append(i++).append(".</code> ");
                if (meal.equalsIgnoreCase(TargetMeal.getTargetMeal(chatId).getName())) {
                    messageText.append("<b>");
                    messageText.append("Meal: ").append(meal);
                    messageText.append("</b>");
                } else {
                    messageText.append("Meal: ").append(meal);
                }
                messageText.append("\n");
            }
        }
        return messageText;
    }

    public void nextMessage(CallbackQuery callbackQuery) {
        Message message = callbackQuery.getMessage();
        String chatId = message.getChatId().toString();
        offset.setSearchOffset(chatId, 1);
        Pageable pageable = new OffsetBasedPageRequest(offset.getSearchOffset(chatId), State.getLimitState(chatId));
        List<MealDto> meals = dailyMealService.getAllByLimit(pageable);
        if (meals.size() == 0) {
            AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery(callbackQuery.getId());
            answerCallbackQuery.setText("Last Page");
            answerCallbackQuery.setShowAlert(false);
            offset.setSearchOffset(chatId, -1);
            bot.executeMessage(answerCallbackQuery);
        } else {
            editMessage(message, chatId, meals);
        }
    }

    public void prevMessage(CallbackQuery callbackQuery) {
        Message message = callbackQuery.getMessage();
        String chatId = message.getChatId().toString();
        offset.setSearchOffset(chatId, -1);
        if (offset.getSearchOffset(chatId) >= 0) {
            Pageable pageable = new OffsetBasedPageRequest(offset.getSearchOffset(chatId), State.getLimitState(chatId));
            List<MealDto> meals = dailyMealService.getAllByLimit(pageable);
            editMessage(message, chatId, meals);
        }
        else {
            AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery(callbackQuery.getId());
            answerCallbackQuery.setText("First Page");
            answerCallbackQuery.setShowAlert(false);
            offset.setSearchOffset(chatId, 1);
            bot.executeMessage(answerCallbackQuery);
        }
    }

    private void editMessage(Message message, String chatId, List<MealDto> meals) {
        TargetMeal.setTargetMeal(chatId, meals.get(0));
        EditMessageMedia editMessageMedia = new EditMessageMedia();
        editMessageMedia.setChatId(chatId);
        editMessageMedia.setMessageId(message.getMessageId());
        InputMediaPhoto inputMediaPhoto = new InputMediaPhoto();
        inputMediaPhoto.setMedia(meals.get(0).getPhotoId());
        inputMediaPhoto.setCaption(getMealMessage(DailyMealName.getDailyMealsNames(chatId), chatId).toString());
        inputMediaPhoto.setParseMode("HTML");
        editMessageMedia.setMedia(inputMediaPhoto);
        editMessageMedia.setReplyMarkup(InlineBoard.dailyMealMenu(meals, State.getLimitState(chatId), offset.getSearchOffset(chatId), chatId));
        bot.executeMessage(editMessageMedia);
    }
}
