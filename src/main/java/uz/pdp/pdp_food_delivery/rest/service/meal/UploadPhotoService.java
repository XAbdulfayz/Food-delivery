package uz.pdp.pdp_food_delivery.rest.service.meal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import uz.pdp.pdp_food_delivery.telegrambot.PdpFoodDeliveryBot;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UploadPhotoService {

    private String UPLOAD_DIRECTORY = "src/main/resources/mealPicture/";
    private final PdpFoodDeliveryBot bot;

    public String upload(MultipartFile mealPhoto) {

        String format = StringUtils.getFilenameExtension(mealPhoto.getOriginalFilename());
        String photoPath = UPLOAD_DIRECTORY + UUID.randomUUID().toString().replace("-", "") + "." + format;
        Path path = Paths.get(photoPath);
        try {
            Files.copy(mealPhoto.getInputStream(), path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return photoPath;
    }

    public void uploadFromTelegram(Message message) {
        String photoId = message.getPhoto().get(0).getFileId();
        GetFile getFile = new GetFile();
        getFile.setFileId(photoId);
        try {
            URL url = new URL("https://api.telegram.org/bot" + bot.getBotToken() + "/getFile?file_id=" + photoId);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            InputStream inputStream = new URL("https://api.telegram.org/bot" + bot.getBotToken() + "/" + photoId).openStream(); //TODO chala
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
