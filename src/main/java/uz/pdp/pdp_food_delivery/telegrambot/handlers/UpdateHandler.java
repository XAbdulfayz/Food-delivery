package uz.pdp.pdp_food_delivery.telegrambot.handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import uz.pdp.pdp_food_delivery.telegrambot.handlers.base.AbstractHandler;
import uz.pdp.pdp_food_delivery.telegrambot.handlers.base.BaseHandler;

@Component
@RequiredArgsConstructor
public class UpdateHandler extends AbstractHandler implements BaseHandler {

    private final MessageHandler messageHandler;
    private final CallbackHandler callbackHandler;
    private final InlineHandler inlineHandler;

    @Override
    public void handle(Update update) {
        if (update.hasMessage()) messageHandler.handle(update);
        else if (update.hasCallbackQuery()) callbackHandler.handle(update);
        else if (update.hasInlineQuery()) inlineHandler.handle(update);
    }

}
