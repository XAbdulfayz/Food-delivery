package uz.pdp.pdp_food_delivery.telegrambot.processors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import uz.pdp.pdp_food_delivery.rest.entity.AuthUser;
import uz.pdp.pdp_food_delivery.rest.repository.auth.AuthUserRepository;
import uz.pdp.pdp_food_delivery.telegrambot.PdpFoodDeliveryBot;
import uz.pdp.pdp_food_delivery.telegrambot.buttons.MarkupBoard;
import uz.pdp.pdp_food_delivery.telegrambot.emojis.Emojis;
import uz.pdp.pdp_food_delivery.telegrambot.enums.MenuState;
import uz.pdp.pdp_food_delivery.telegrambot.states.State;

@Component
@RequiredArgsConstructor
public class MenuProcessor {
    private final AuthUserRepository authUserRepository;
    private final PdpFoodDeliveryBot bot;

    public void menu(Message message) {
        String text = message.getText();
        String chatId = message.getChatId().toString();

        if (State.getMenuState(chatId).equals(MenuState.UNDEFINED)) {
            AuthUser user = authUserRepository.getByChatId(chatId);
            if ("USER".equals(user.getRole().toString()) || "ADMIN".equals(user.getRole().toString())) {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setText("Order some meals " + Emojis.DOWNLOAD);
                sendMessage.setChatId(chatId);
                sendMessage.setReplyMarkup(MarkupBoard.menuUser());
                bot.executeMessage(sendMessage);
            } else if ("FOOD_MANAGER".equals(user.getRole().toString())) {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setText("MENUS FOR FOOD MANAGER " + Emojis.DOWNLOAD);
                sendMessage.setChatId(chatId);
                sendMessage.setReplyMarkup(MarkupBoard.menuFoodManager());
                bot.executeMessage(sendMessage);
            }

        }

    }
}
