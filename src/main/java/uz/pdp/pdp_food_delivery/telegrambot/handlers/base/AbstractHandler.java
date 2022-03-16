package uz.pdp.pdp_food_delivery.telegrambot.handlers.base;

import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class AbstractHandler {
    public abstract void handle(Update update);
}
