package uz.pdp.pdp_food_delivery.telegrambot.buttons;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import uz.pdp.pdp_food_delivery.rest.dto.meal.MealDto;
import uz.pdp.pdp_food_delivery.rest.enums.FeedbackType;
import uz.pdp.pdp_food_delivery.telegrambot.emojis.Emojis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InlineBoard {

    private static final InlineKeyboardMarkup board = new InlineKeyboardMarkup();


    public static InlineKeyboardMarkup languageButtons() {
        InlineKeyboardButton uz = new InlineKeyboardButton(Emojis.UZ + " O'zbek");
        uz.setCallbackData("uz");

        InlineKeyboardButton ru = new InlineKeyboardButton(Emojis.RU + " Русский");
        ru.setCallbackData("ru");

        InlineKeyboardButton en = new InlineKeyboardButton(Emojis.EN + " English");
        en.setCallbackData("en");
        board.setKeyboard(Arrays.asList(getRow(uz), getRow(ru), getRow(en)));
        return board;
    }

    //
    public static InlineKeyboardMarkup accept(String chatId) {
        InlineKeyboardButton accept = new InlineKeyboardButton(Emojis.ADD + " Confirm");
        accept.setCallbackData("accept_" + chatId);
        InlineKeyboardButton no = new InlineKeyboardButton(Emojis.REMOVE + " Ignore");
        no.setCallbackData("accept_no");
        board.setKeyboard(Arrays.asList(getRow(accept), getRow(no)));
        return board;
    }

    private static List<InlineKeyboardButton> getRow(InlineKeyboardButton... buttons) {
        return Arrays.stream(buttons).toList();
    }

    public static InlineKeyboardMarkup meal(List<MealDto> meals) {
        InlineKeyboardMarkup board = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        List<InlineKeyboardButton> numberButtons = new ArrayList<>();
        List<InlineKeyboardButton> numberButtons1 = new ArrayList<>();
        List<String> numbers = new ArrayList<>(Arrays.asList("1️⃣", "2️⃣", "3️⃣", "4️⃣", "5️⃣", "6️⃣", "7️⃣", "8️⃣", "9️⃣", "\uD83D\uDD1F"));
        int i = 1;
        if (meals.size() <= 5) {
            for (MealDto meal : meals) {
                InlineKeyboardButton button = new InlineKeyboardButton(numbers.get(i++ - 1));
                button.setCallbackData("add_" + meal.getId().toString());
                numberButtons.add(button);
            }
            buttons.add(numberButtons);
        } else {
            for (int j = 0; j < meals.size(); j++) {
                if (j >= meals.size() / 2) {
                    InlineKeyboardButton button = new InlineKeyboardButton(numbers.get(i++ - 1));
                    button.setCallbackData("add_" + meals.get(j).getId().toString());
                    numberButtons1.add(button);
                } else {
                    InlineKeyboardButton button = new InlineKeyboardButton(numbers.get(i++ - 1));
                    button.setCallbackData("add_" + meals.get(j).getId().toString());
                    numberButtons.add(button);
                }
            }
            buttons.add(numberButtons);
            buttons.add(numberButtons1);
        }

        List<InlineKeyboardButton> extraButtons = new ArrayList<>();

        InlineKeyboardButton cancelButton = new InlineKeyboardButton(Emojis.REMOVE);
        cancelButton.setCallbackData("cancel");
        extraButtons.add(cancelButton);
        buttons.add(extraButtons);
        board.setKeyboard(buttons);
        return board;
    }

    public static InlineKeyboardMarkup dailyMealMenu(List<MealDto> meals, Integer limit, Integer offset, String chatId) {
        InlineKeyboardMarkup board = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        List<InlineKeyboardButton> numberButtons = new ArrayList<>();
        if (meals.size() <= 5) {
            for (MealDto meal : meals) {
//                InlineKeyboardButton button = new InlineKeyboardButton(LangConfig.get(chatId, "order"));
                InlineKeyboardButton button = new InlineKeyboardButton("ORDER");
                button.setCallbackData("order_" + meal.getId());
                numberButtons.add(button);
            }
            buttons.add(numberButtons);
        }

        List<InlineKeyboardButton> extraButtons = new ArrayList<>();
        InlineKeyboardButton prevButton = new InlineKeyboardButton(Emojis.PREVIOUS);
        prevButton.setCallbackData("prev");
        extraButtons.add(prevButton);
        InlineKeyboardButton cancelButton = new InlineKeyboardButton(Emojis.REMOVE);
        cancelButton.setCallbackData("cancel");
        extraButtons.add(cancelButton);
        if (meals.size() == limit) {
            InlineKeyboardButton nextButton = new InlineKeyboardButton(Emojis.NEXT);
            nextButton.setCallbackData("next");
            extraButtons.add(nextButton);
        }
        buttons.add(extraButtons);
        board.setKeyboard(buttons);
        return board;
    }

    public static InlineKeyboardMarkup cronJobChooseButton() {
        InlineKeyboardButton choose = new InlineKeyboardButton("Order");
        choose.setCallbackData("Order");
        board.setKeyboard(List.of(getRow(choose)));
        return board;
    }

    public static InlineKeyboardMarkup cronJobgetOrderButton() {
        InlineKeyboardButton yes = new InlineKeyboardButton("Yes");
        yes.setCallbackData("Yes");
        InlineKeyboardButton no = new InlineKeyboardButton("No");
        no.setCallbackData("No");
        board.setKeyboard(Arrays.asList(getRow(no), getRow(yes)));
        return board;
    }

    public static InlineKeyboardMarkup feedbackButtons(String chatId) {
        List<List<InlineKeyboardButton>> feedbackList = new ArrayList<>();
        for (FeedbackType feedbackType : FeedbackType.values()) {
            InlineKeyboardButton button = new InlineKeyboardButton(feedbackType.toString());
            button.setCallbackData("feedback_" + feedbackType);
            feedbackList.add(getRow(button));
        }
        board.setKeyboard(feedbackList);
        return board;
    }
}
