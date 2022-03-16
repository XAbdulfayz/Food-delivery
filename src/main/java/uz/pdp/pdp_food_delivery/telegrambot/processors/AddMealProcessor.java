package uz.pdp.pdp_food_delivery.telegrambot.processors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ForceReplyKeyboard;
import uz.pdp.pdp_food_delivery.rest.dto.meal.MealCreateDto;
import uz.pdp.pdp_food_delivery.rest.service.meal.MealService;
import uz.pdp.pdp_food_delivery.rest.service.meal.UploadPhotoService;
import uz.pdp.pdp_food_delivery.telegrambot.PdpFoodDeliveryBot;
import uz.pdp.pdp_food_delivery.telegrambot.config.TargetMeal;
import uz.pdp.pdp_food_delivery.telegrambot.emojis.Emojis;
import uz.pdp.pdp_food_delivery.telegrambot.enums.AddMealState;
import uz.pdp.pdp_food_delivery.telegrambot.states.State;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AddMealProcessor {

    private final PdpFoodDeliveryBot bot;
    private final MealService mealService;
    private final UploadPhotoService uploadPhotoService;

    public void process(Message message, AddMealState addBookState) {
        String chatId = message.getChatId().toString();
        if (addBookState.equals(AddMealState.UNDEFINED)) {
//            SendMessage sendMessage = new SendMessage(chatId, LangConfig.get(chatId, "upload.photo"));
            SendMessage sendMessage = new SendMessage(chatId, "upload photo");
            bot.executeMessage(sendMessage);
            State.setAddMealState(chatId, AddMealState.FILE);
        } else if (addBookState.equals(AddMealState.FILE)) {
            if (message.hasPhoto()) {
                String photoId = message.getPhoto().get(0).getFileId();
                List<PhotoSize> photo = message.getPhoto();
                PhotoSize photoSize = photo.get(0);

                TargetMeal.setMealId(chatId, photoId);
                mealService.create(new MealCreateDto(photoId, message.getCaption()));
//                SendMessage sendMessage = new SendMessage(chatId,
//                        Emojis.ADD + " " + LangConfig.get(chatId, "photo.uploaded"));
                SendMessage sendMessage = new SendMessage(chatId,
                        Emojis.ADD + "uploaded");
                bot.executeMessage(sendMessage);
                State.setAddMealState(chatId, AddMealState.UNDEFINED);
                //               uploadPhotoService.uploadFromTelegram(message); TODO chala
            } else {
//                SendMessage sendMessage = new SendMessage(chatId, LangConfig.get(chatId, "upload.photo.again"));
                SendMessage sendMessage = new SendMessage(chatId, "upload photo again");
                sendMessage.setReplyMarkup(new ForceReplyKeyboard());
                bot.executeMessage(sendMessage);
            }
        }
    }
}
