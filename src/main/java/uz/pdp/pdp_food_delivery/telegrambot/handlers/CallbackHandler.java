package uz.pdp.pdp_food_delivery.telegrambot.handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ForceReplyKeyboard;
import uz.pdp.pdp_food_delivery.rest.dto.auth.AuthUserDto;
import uz.pdp.pdp_food_delivery.rest.dto.dailymeal.DailyMealCreateDto;
import uz.pdp.pdp_food_delivery.rest.dto.dailymeal.DailyMealDto;
import uz.pdp.pdp_food_delivery.rest.dto.feedback.FeedbackCreateDto;
import uz.pdp.pdp_food_delivery.rest.dto.meal.MealDto;
import uz.pdp.pdp_food_delivery.rest.dto.mealorder.MealOrderCreateDto;
import uz.pdp.pdp_food_delivery.rest.entity.AuthUser;
import uz.pdp.pdp_food_delivery.rest.enums.FeedbackType;
import uz.pdp.pdp_food_delivery.rest.enums.Role;
import uz.pdp.pdp_food_delivery.rest.repository.auth.AuthUserRepository;
import uz.pdp.pdp_food_delivery.rest.service.auth.AuthUserService;
import uz.pdp.pdp_food_delivery.rest.service.dailymeal.DailyMealService;
import uz.pdp.pdp_food_delivery.rest.service.feedback.FeedbackService;
import uz.pdp.pdp_food_delivery.rest.service.meal.MealService;
import uz.pdp.pdp_food_delivery.rest.service.mealorder.MealOrderService;
import uz.pdp.pdp_food_delivery.telegrambot.PdpFoodDeliveryBot;
import uz.pdp.pdp_food_delivery.telegrambot.buttons.MarkupBoard;
import uz.pdp.pdp_food_delivery.telegrambot.config.Offset;
import uz.pdp.pdp_food_delivery.telegrambot.config.TargetFeedback;
import uz.pdp.pdp_food_delivery.telegrambot.enums.*;
import uz.pdp.pdp_food_delivery.telegrambot.handlers.base.AbstractHandler;
import uz.pdp.pdp_food_delivery.telegrambot.processors.CallbackHandlerProcessor;
import uz.pdp.pdp_food_delivery.telegrambot.processors.OrderMealProcessor;
import uz.pdp.pdp_food_delivery.telegrambot.states.State;

import java.time.LocalDate;
import java.util.Objects;

import static uz.pdp.pdp_food_delivery.telegrambot.states.State.setState;

@Component
@RequiredArgsConstructor
public class CallbackHandler extends AbstractHandler {

    private final AuthUserRepository authUserRepository;
    private final PdpFoodDeliveryBot bot;
    private final MealService mealService;
    private final MealOrderService mealOrderService;
    private final AuthUserService authUserService;
    private final DailyMealService dailyMealService;
    private final FeedbackService feedbackService;
    private final Offset offset;
    private final CallbackHandlerProcessor callbackHandlerProcessor;
    private final OrderMealProcessor orderMealProcessor;

    @Override
    public void handle(Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        Message message = callbackQuery.getMessage();
        String data = callbackQuery.getData();
        String chatId = message.getChatId().toString();
// this  method for cron job
        if (data.equals("Order")) {
            orderMealProcessor.process(update);
            deleteMessage(message, chatId);
        } else if (data.equals("Yes")) {
            orderMealProcessor.setOrderMealsByDone(chatId);
            deleteMessage(message, chatId);
        } else if (data.equals("No")) {
            deleteMessage(message, chatId);
        }
//

        if ("uz".equals(data) || "ru".equals(data) || "en".equals(data)) {
            AuthUser user = authUserRepository.getByChatId(chatId);
            user.setLanguage(Language.getByCode(data));
            State.setLanguageState(chatId, Language.getByCode(data));
            authUserRepository.save(user);
            SendMessage sendMessage = new SendMessage(chatId, "Enter your Fullname: ");
            sendMessage.setReplyMarkup(new ForceReplyKeyboard());
            setState(chatId, UState.FULL_NAME);
            deleteMessage(message, chatId);
            State.setLanguageState(chatId, Language.getByCode(data));
            bot.executeMessage(sendMessage);
        } else if (data.startsWith("accept_")) {
            String acceptedUser = data.substring(7);
            if (data.substring(7).equals("no")) {
                SendMessage sendMessage = new SendMessage(chatId, "User not Accepted");
                DeleteMessage deleteMessage = new DeleteMessage(chatId, message.getMessageId());
                bot.executeMessage(deleteMessage);
                bot.executeMessage(sendMessage);
            } else {
                AuthUser user = authUserRepository.getByChatId(acceptedUser);
                user.setRole(Role.USER);
                user.setActive(true);
                State.setState(acceptedUser, UState.AUTHORIZED);
                State.setMenuState(acceptedUser, MenuState.UNDEFINED);
                authUserRepository.save(user);
                DeleteMessage deleteMessage = new DeleteMessage(chatId, message.getMessageId());
                bot.executeMessage(deleteMessage);
                SendMessage sendMessage = new SendMessage(chatId, "User successfully added!");
                bot.executeMessage(sendMessage);
                SendMessage sendMessage1 = new SendMessage(acceptedUser, "You are successfully registered");
                sendMessage1.setReplyMarkup(MarkupBoard.menuUser());
                bot.executeMessage(sendMessage1);
            }
        } else if (data.equals("prev")) {
            callbackHandlerProcessor.prevMessage(callbackQuery);
            deleteMessage(message, chatId);
        } else if (data.equals("next")) {
            callbackHandlerProcessor.nextMessage(callbackQuery);
            deleteMessage(message, chatId);
        } else if (data.equals("cancel")) {
            offset.setSearchOffset(chatId, 0);
            deleteMessage(message, chatId);
            State.setMenuState(chatId, MenuState.UNDEFINED);
            State.setSearchState(chatId, SearchState.UNDEFINED);
        } else if (data.startsWith("order_")) {
            String splitData = data.substring(6);
            DailyMealDto dailyMealDto = dailyMealService.get(Long.valueOf(splitData));
            MealDto mealDto = mealService.getByPhotoId(dailyMealDto.getPhotoId());
            AuthUserDto authUserDto = authUserService.getByChatId(chatId);
            mealOrderService.create(new MealOrderCreateDto(authUserDto, mealDto));
            SendMessage sendMessage = new SendMessage(chatId, mealDto.getName());
            bot.executeMessage(sendMessage);
            deleteMessage(message, chatId);
        } else if (data.startsWith("add_")) {
            String splitData = data.substring(4);
            MealDto mealDto = mealService.get(Long.valueOf(splitData));
            SendMessage sendMessage = new SendMessage(chatId, mealDto.getName());

            if (Objects.nonNull(dailyMealService.get(mealDto.getId()))) {
                sendMessage.setText("this meal was added to today`s daily");
            } else if (mealDto.getPhotoId() == null) {
                SendPhoto sendPhoto = new SendPhoto();
                sendPhoto.setChatId(chatId);
                mealDto.setPhotoId(mealService.updateMealPhotoId(mealDto.getPhotoPath(), sendPhoto));
            } else
                dailyMealService.create(new DailyMealCreateDto(mealDto.getName(), LocalDate.now(), mealDto.getPhotoId()));
            bot.executeMessage(sendMessage);
            deleteMessage(message, chatId);
        } else if (data.startsWith("feedback_")) {
            String splitData = data.substring(9);
            FeedbackType feedbackType = FeedbackType.valueOf(splitData);
            FeedbackCreateDto dto = TargetFeedback.getTargetFeedback(chatId);
            dto.setFeedbackType(feedbackType);
            TargetFeedback.setTargetFeedback(chatId, dto);
            feedbackService.create(TargetFeedback.getTargetFeedback(chatId));
            State.setFeedbackState(chatId, FeedbackState.UNDEFINED);
            SendMessage sendMessage = new SendMessage(chatId, "feedback saved");
            bot.executeMessage(sendMessage);
            deleteMessage(message, chatId);
        }


    }

    private void deleteMessage(Message message, String chatId) {
        DeleteMessage deleteMessage = new DeleteMessage(chatId, message.getMessageId());
        bot.executeMessage(deleteMessage);
    }
}
