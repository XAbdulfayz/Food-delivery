package uz.pdp.pdp_food_delivery.telegrambot.buttons;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import uz.pdp.pdp_food_delivery.telegrambot.LangConfig;
import uz.pdp.pdp_food_delivery.telegrambot.emojis.Emojis;

import java.util.List;

public class MarkupBoard {
    private static final ReplyKeyboardMarkup board = new ReplyKeyboardMarkup();

    public static ReplyKeyboardMarkup userMenu(String chatId) {
        KeyboardRow row = new KeyboardRow();
        row.add(new KeyboardButton(Emojis.HISTORY + " " + LangConfig.get(chatId, "order.history")));
        row.add(new KeyboardButton(Emojis.SETTINGS + " " + LangConfig.get(chatId, "settings")));

        board.setKeyboard(List.of(row));
        board.setResizeKeyboard(true);
        board.setSelective(true);
        return board;
    }

    public static ReplyKeyboardMarkup department() {
        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton("ACADEMIC"));
        row1.add(new KeyboardButton("SALES"));
        KeyboardRow row2 = new KeyboardRow();
        row2.add(new KeyboardButton("HR"));
        row2.add(new KeyboardButton("SERVICE"));
        KeyboardRow row3 = new KeyboardRow();
        row3.add(new KeyboardButton("MARKETING"));
        row3.add(new KeyboardButton("MENTORS"));
        KeyboardRow row4 = new KeyboardRow();
        row4.add(new KeyboardButton("ECMA"));
        row4.add(new KeyboardButton("ACCOUNTING"));
        KeyboardRow row5 = new KeyboardRow();
        row5.add(new KeyboardButton("UNICORN"));
        row5.add(new KeyboardButton("HEAD"));
        board.setKeyboard(List.of(row1, row2, row3, row4, row5));
        board.setResizeKeyboard(true);
        board.setSelective(true);
        return board;
    }

    public static ReplyKeyboardMarkup menuUser() {
        KeyboardRow row = new KeyboardRow();
        row.add(new KeyboardButton("Order"));
        row.add(new KeyboardButton("FeedBack"));
        board.setSelective(true);
        board.setResizeKeyboard(true);
        board.setKeyboard(List.of(row));
        return board;
    }

    public static ReplyKeyboardMarkup menuFoodManager() {
        KeyboardRow row = new KeyboardRow();
        KeyboardRow row1 = new KeyboardRow();
        row.add(new KeyboardButton("Add Meal"));
        row.add(new KeyboardButton("Order"));
//        row.add(new KeyboardButton("File"));
        row1.add(new KeyboardButton("Add Daily Meals"));
        row1.add(new KeyboardButton("FeedBack"));
        row1.add(new KeyboardButton("Monitoring"));
        board.setResizeKeyboard(true);
        board.setSelective(true);
        board.setKeyboard(List.of(row, row1));
        return board;
    }

    public static ReplyKeyboardMarkup sharePhoneNumber() {
        KeyboardButton phoneContact = new KeyboardButton(Emojis.PHONE + " " + "Contact");
        phoneContact.setRequestContact(true);
        board.setResizeKeyboard(true);
        board.setSelective(true);
        KeyboardRow row = new KeyboardRow();
        row.add(phoneContact);
        board.setKeyboard(List.of(row));
        return board;
    }


}
