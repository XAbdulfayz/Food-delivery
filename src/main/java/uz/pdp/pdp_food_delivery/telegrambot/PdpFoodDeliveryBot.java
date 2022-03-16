package uz.pdp.pdp_food_delivery.telegrambot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageMedia;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import uz.pdp.pdp_food_delivery.rest.repository.auth.AuthUserRepository;
import uz.pdp.pdp_food_delivery.rest.service.auth.AuthUserService;
import uz.pdp.pdp_food_delivery.telegrambot.handlers.UpdateHandler;
import uz.pdp.pdp_food_delivery.telegrambot.processors.AuthorizationProcessor;

import java.util.*;

@Component
public class PdpFoodDeliveryBot extends TelegramLongPollingBot {

    private final AuthUserService authUserService;
    private final UpdateHandler updateHandler;
    private final AuthorizationProcessor authorizationProcessor;
    private final AuthUserRepository authUserRepository;



    @Value("${bot.name}")
    private String botUsername;

//    @Value("${bot.token}")
    private String botToken = "1765449966:AAFcVYwuohlQYRll3ZMreLgEIMO9mBefs-g";

    public PdpFoodDeliveryBot(AuthUserService authUserService, @Lazy UpdateHandler updateHandler, @Lazy AuthorizationProcessor authorizationProcessor, AuthUserRepository authUserRepository) {
        this.authUserService = authUserService;
        this.updateHandler = updateHandler;
        this.authorizationProcessor = authorizationProcessor;
        this.authUserRepository = authUserRepository;
    }


    @Override
    public void onUpdateReceived(Update update) {
        updateHandler.handle(update);
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }


    public Message executeMealPicture(SendPhoto msg) {
        try {
            return execute(msg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void executeMessage(SendMessage msg) {
        msg.setParseMode("HTML");
        try {
            execute(msg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void executeMessage(SendPhoto msg) {
        msg.setParseMode("HTML");
        try {
            execute(msg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void executeMessage(SendDocument msg) {
        try {
            execute(msg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void executeMessage(EditMessageText msg) {
        msg.setParseMode("HTML");
        try {
            execute(msg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void executeMessage(DeleteMessage deleteMessage) {
        try {
            execute(deleteMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void executeMessage(EditMessageMedia editMessageMedia) {
        try {
            execute(editMessageMedia);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void executeMessage(AnswerCallbackQuery answerCallbackQuery) {
        try {
            execute(answerCallbackQuery);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
