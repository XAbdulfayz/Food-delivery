package uz.pdp.pdp_food_delivery.telegrambot.handlers;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery;
import uz.pdp.pdp_food_delivery.telegrambot.handlers.base.AbstractHandler;

@Component
public class InlineHandler extends AbstractHandler {
    @Override
    public void handle(Update update) {
        InlineQuery inlineQuery = update.getInlineQuery();
    }
}
