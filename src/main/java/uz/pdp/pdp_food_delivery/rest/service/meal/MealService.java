package uz.pdp.pdp_food_delivery.rest.service.meal;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import uz.pdp.pdp_food_delivery.rest.dto.meal.MealCreateDto;
import uz.pdp.pdp_food_delivery.rest.dto.meal.MealDto;
import uz.pdp.pdp_food_delivery.rest.dto.meal.MealUpdateDto;
import uz.pdp.pdp_food_delivery.rest.entity.meal.Meal;
import uz.pdp.pdp_food_delivery.rest.mapper.meal.MealMapper;
import uz.pdp.pdp_food_delivery.rest.repository.meal.MealRepository;
import uz.pdp.pdp_food_delivery.rest.service.base.AbstractService;
import uz.pdp.pdp_food_delivery.rest.service.base.BaseService;
import uz.pdp.pdp_food_delivery.rest.service.base.GenericCrudService;
import uz.pdp.pdp_food_delivery.rest.service.base.GenericService;
import uz.pdp.pdp_food_delivery.telegrambot.PdpFoodDeliveryBot;

import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class MealService extends AbstractService<MealMapper, MealRepository>
        implements GenericCrudService<Meal,MealDto,MealCreateDto, MealUpdateDto>, GenericService<MealDto>, BaseService {

    private final UploadPhotoService uploadPhotoService;
    private final PdpFoodDeliveryBot bot;

    public MealService(MealMapper mapper, MealRepository repository, UploadPhotoService uploadPhotoService, PdpFoodDeliveryBot bot) {
        super(mapper, repository);
        this.uploadPhotoService = uploadPhotoService;
        this.bot = bot;
    }

    @Override
    public Long create(MealCreateDto mealCreateDto) {

        Meal meal = mapper.fromCreateDto(mealCreateDto);
//        meal.setPhotoPath(uploadPhotoService.upload(mealCreateDto.getPicture()));
        meal.setDate(LocalDate.now());

        Meal save = repository.save(meal);
        return save.getId();
    }

    public Long create(MealCreateDto mealCreateDto, Long sesId) {

        Meal meal = mapper.fromCreateDto(mealCreateDto);
        meal.setPicture(uploadPhotoService.upload(mealCreateDto.getPicture()));
        meal.setCreatedBy(sesId);

        repository.save(meal);

        return meal.getId();

    }

    @Override
    public void delete(Long id) {
        Optional<Meal> mealOp = repository.findByIdAndDeleted(id, false);
        Meal meal = mealOp.get();
        mealOp.ifPresentOrElse(
                (value) ->
                        value.setDeleted(true),
                () -> {
                    throw new RuntimeException("meal not found");
                });
        repository.save(meal);
    }

    public void delete(Long id, Long sesId) {
        Optional<Meal> mealOp = repository.findByIdAndDeleted(id, false);
        Meal meal = mealOp.get();
        mealOp.ifPresentOrElse(
                (value) ->
                        value.setDeleted(true),
                () -> {
                    throw new RuntimeException("meal not found");
                });
        meal.setUpdatedBy(sesId);
        repository.save(meal);
    }

    @Override
    public void update(MealUpdateDto mealUpdateDto) {

        Meal meal = findById(mealUpdateDto.getId());

        mapper.fromUpdateDto(mealUpdateDto, meal);

        if (Objects.nonNull(mealUpdateDto.getPicture())) {
            meal.setPicture(uploadPhotoService.upload(mealUpdateDto.getPicture()));
            meal.setPhotoId(null);
        }

        repository.save(meal);
    }

    public void update(MealUpdateDto mealUpdateDto, Long sesId) {

        Meal meal = findById(mealUpdateDto.getId());

        mapper.fromUpdateDto(mealUpdateDto, meal);

        if (Objects.nonNull(mealUpdateDto.getPicture())) {
            meal.setPicture(uploadPhotoService.upload(mealUpdateDto.getPicture()));
            meal.setPhotoId(null);
        }
        meal.setUpdatedBy(sesId);

        repository.save(meal);

    }

    public String updateMealPhotoId(String photoPath, SendPhoto sendPhoto) {

        Optional<Meal> mealOptional = repository.findByPicture(photoPath);
        Meal meal = mealOptional.get();

        sendPhoto.setPhoto(new InputFile(new File(photoPath)));
        Message message = bot.executeMealPicture(sendPhoto);
        String photoId = message.getPhoto().get(0).getFileId();
        meal.setPhotoId(photoId);
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setMessageId(message.getMessageId());
        deleteMessage.setChatId(sendPhoto.getChatId());
        bot.executeMessage(deleteMessage);
        repository.save(meal);
        return photoId;
    }

    private Meal findById(Long id) {
        Optional<Meal> mealOp = repository.findById(id);

        return mealOp.get();
    }

    @Override
    public List<MealDto> getAll() {
        List<Meal> meals = repository.findAll();
        return mapper.toDto(meals);
    }

    @Override
    public MealDto get(Long id) {
        Meal meal = repository.findById(id).orElseThrow(() -> new RuntimeException("Meal Not Found"));
        return mapper.toDto(meal);
    }

    public List<MealDto> getAllByLimit(Pageable pageable) {
        List<Meal> meals = repository.findAll(pageable).getContent();
        return mapper.toDto(meals);
    }

    public MealDto getByPhotoId(String photoId) {
        Meal meal = repository.findByPhotoId(photoId);
        return mapper.toDto(meal);
    }
}
