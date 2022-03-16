package uz.pdp.pdp_food_delivery.telegrambot;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.pdp.pdp_food_delivery.rest.repository.auth.AuthUserRepository;
import uz.pdp.pdp_food_delivery.rest.service.auth.AuthUserService;
import uz.pdp.pdp_food_delivery.telegrambot.enums.Language;
import uz.pdp.pdp_food_delivery.telegrambot.states.State;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

@Component
@RequiredArgsConstructor
public class LangConfig {
    public static Properties uz;
    public static Properties ru;
    public static Properties en;
    public static String pathPre = "src/main/resources/i18n/";

    private final AuthUserService authUserService;
    private final AuthUserRepository authUserRepository;

    static {
        load();
    }

    private static void load() {
        try (FileReader uzFileReader = new FileReader(pathPre + "uz.properties");
             FileReader ruFileReader = new FileReader(pathPre + "ru.properties");
             FileReader enFileReader = new FileReader(pathPre + "en.properties")) {
            uz = new Properties();
            ru = new Properties();
            en = new Properties();
            uz.load(uzFileReader);
            ru.load(ruFileReader);
            en.load(enFileReader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String get(String chatId, String key) {
        Language language = State.getLanguageState(chatId);
        if (language.getCode().equalsIgnoreCase("uz"))
            return uz.getProperty(key);
        if (language.getCode().equalsIgnoreCase("ru"))
            return ru.getProperty(key);
        return en.getProperty(key);
    }
}
