package uz.pdp.pdp_food_delivery.telegrambot.handlers.cron;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import uz.pdp.pdp_food_delivery.rest.repository.auth.AuthUserRepository;
import uz.pdp.pdp_food_delivery.telegrambot.LangConfig;
import uz.pdp.pdp_food_delivery.telegrambot.PdpFoodDeliveryBot;
import uz.pdp.pdp_food_delivery.telegrambot.buttons.InlineBoard;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CronJob {

    private final AuthUserRepository repository;
    private final PdpFoodDeliveryBot bot;

    //    @Scheduled(cron="0 0/30 8,9,10,18,19,20 ? * MON,TUE,WED,THU,FRI")
//    @Scheduled(cron="0/30 * * * * *")
    private void chooseCronJob() {
        List<Long> list = repository.getUserIdByNoMealOrder().get();
        for (Long item : list) {
            SendMessage message = new SendMessage();
            message.setChatId(item.toString());
            message.setText(LangConfig.get(item.toString(), "CHOOSECRONJOB"));
            InlineKeyboardMarkup board = InlineBoard.cronJobChooseButton();
            message.setReplyMarkup(board);
            bot.executeMessage(message);
        }
    }

//    @Scheduled(cron = "0 0/30 12,13,14 ? * MON,TUE,WED,THU,FRI")
//    @Scheduled(cron = "0/30 * * * * *")
    private void getCronJob() {
        List<Long> list = repository.getUserIdByMealOrder().get();
        list.forEach(item -> {
            SendMessage message = new SendMessage();
            message.setChatId(item.toString());
            message.setText(LangConfig.get(item.toString(), "GETORDERCRONJOB"));
            InlineKeyboardMarkup board = InlineBoard.cronJobgetOrderButton();
            message.setReplyMarkup(board);
            bot.executeMessage(message);
        });
    }
}

