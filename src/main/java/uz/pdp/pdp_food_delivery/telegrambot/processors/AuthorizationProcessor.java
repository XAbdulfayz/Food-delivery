package uz.pdp.pdp_food_delivery.telegrambot.processors;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ForceReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import uz.pdp.pdp_food_delivery.rest.entity.AuthUser;
import uz.pdp.pdp_food_delivery.rest.enums.Department;
import uz.pdp.pdp_food_delivery.rest.enums.Role;
import uz.pdp.pdp_food_delivery.rest.repository.auth.AuthUserRepository;
import uz.pdp.pdp_food_delivery.telegrambot.PdpFoodDeliveryBot;
import uz.pdp.pdp_food_delivery.telegrambot.buttons.InlineBoard;
import uz.pdp.pdp_food_delivery.telegrambot.buttons.MarkupBoard;
import uz.pdp.pdp_food_delivery.telegrambot.emojis.Emojis;
import uz.pdp.pdp_food_delivery.telegrambot.enums.UState;
import uz.pdp.pdp_food_delivery.telegrambot.states.State;

import java.util.Arrays;

import static uz.pdp.pdp_food_delivery.telegrambot.states.State.setState;

@Component
@RequiredArgsConstructor
public class AuthorizationProcessor {
    private final PdpFoodDeliveryBot bot;
    private final AuthUserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final MenuProcessor menuProcessor;

    public void process(Message message) {
        String chatId=message.getChatId().toString();
        String text=message.getText();
        if (UState.ANONYMOUS.equals(State.getState(chatId))) {
            String text1 = """
                    Tilni tanlang:
                    Выберите язык:
                    Choose your language:""";
            SendMessage sendMessage = new SendMessage(chatId, text1);
            sendMessage.setReplyMarkup(InlineBoard.languageButtons());
            bot.executeMessage(sendMessage);
            setState(chatId, UState.FULL_NAME);
        }
        else if (UState.FULL_NAME.equals(State.getState(chatId))) {
            if (StringUtils.isNumeric(text) || !text.equals(StringUtils.capitalize(text))) {
                SendMessage sendMessage = new SendMessage(chatId,
                        Emojis.LOOK + " " + "Please, enter your full name correctly" + "\n"
                                + "Without any numbers and capitalize it");
                sendMessage.setReplyMarkup(new ForceReplyKeyboard());
                bot.executeMessage(sendMessage);
            }
            else {
                AuthUser user = repository.getByChatId(chatId);
                user.setFullName(text);
                repository.save(user);
                setState(chatId, UState.EMAIL);
                SendMessage sendMessage = new SendMessage(chatId, "Enter Email");
                bot.executeMessage(sendMessage);
            }
        }
        else if (UState.EMAIL.equals(State.getState(chatId))) {

            AuthUser user = repository.getByChatId(chatId);
            user.setEmail(text);
            repository.save(user);
            setState(chatId, UState.PASSWORD);
            SendMessage sendMessage = new SendMessage();
            sendMessage.setText("Enter Your Password: ");
            sendMessage.setChatId(chatId);
            bot.executeMessage(sendMessage);
        }
        else if (UState.PASSWORD.equals(State.getState(chatId))) {
            AuthUser user = repository.getByChatId(chatId);
            user.setPassword(text);
            repository.save(user);
            SendMessage sendMessage1 = new SendMessage();
            sendMessage1.setText("Confirm your password, please");
            sendMessage1.setChatId(chatId);
            setState(chatId, UState.CONFIRM);
            bot.executeMessage(sendMessage1);
        }
        else if(UState.CONFIRM.equals(State.getState(chatId))){
            AuthUser user = repository.getByChatId(chatId);
            if (!text.equals(user.getPassword())) {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setText("Confirmation password is not true!, please try again: ");
                sendMessage.setChatId(chatId);
                sendMessage.setReplyMarkup(new ForceReplyKeyboard());
                bot.executeMessage(sendMessage);
            } else {
                user.setPassword(passwordEncoder.encode(text));
                repository.save(user);
                SendMessage sendMessage = new SendMessage();
                sendMessage.setReplyMarkup(MarkupBoard.sharePhoneNumber());
                sendMessage.setChatId(chatId);
                sendMessage.setText("Share your Phone number: ");
                setState(chatId, UState.PHONE_NUMBER);
                bot.executeMessage(sendMessage);
            }

        }
        else if (UState.PHONE_NUMBER.equals(State.getState(chatId))) {

            if (message.hasContact()) {
                String phoneNumber = message.getContact().getPhoneNumber();
                AuthUser user = repository.getByChatId(chatId);
                user.setPhoneNumber(phoneNumber);
                ReplyKeyboardMarkup department = MarkupBoard.department();
                SendMessage sendMessage = new SendMessage();
                sendMessage.setReplyMarkup(department);
                sendMessage.setText("Choose your department: ");
                sendMessage.setChatId(chatId);
                setState(chatId, UState.DEPARTMENT);
                repository.save(user);
                bot.executeMessage(sendMessage);
            } else {
                SendMessage sendMessage = new SendMessage(chatId,
                        Emojis.REMOVE + " " + "Invalid Number");
                bot.executeMessage(sendMessage);
            }
        }
        else if (UState.DEPARTMENT.equals(State.getState(chatId))) {

            if (Arrays.stream(Department.values()).noneMatch(department -> department.toString().equals(text))) {
                DeleteMessage deleteMessage = new DeleteMessage(chatId, message.getMessageId());
                bot.executeMessage(deleteMessage);
            }
            AuthUser user = repository.getByChatId(chatId);
            user.setDepartment(Department.getByName(text));
            repository.save(user);

            AuthUser currentUser = repository.getByChatId(chatId);
            AuthUser admin = repository.getByDepartmentAndRole(Department.getByName(text), Role.valueOf("ADMIN"));
            SendMessage sendMessage1= new SendMessage(chatId, "Please wait, your request sent to ADMIN of your Department\n " +
                    "ADMIN should Confirm you");
            bot.executeMessage(sendMessage1);
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(admin.getChatId());
            sendMessage.setText(currentUser.getFullName() + " wants to Join your department,\nWill you confirm?");
            sendMessage.setReplyMarkup(InlineBoard.accept(chatId));

            bot.executeMessage(sendMessage);
        }else if(UState.AUTHORIZED.equals(State.getState(chatId))){
            menuProcessor.menu(message);
        }

    }
}