package uz.pdp.pdp_food_delivery.rest.service.mealorder;

import org.springframework.stereotype.Service;
import uz.pdp.pdp_food_delivery.rest.dto.mealorder.MealOrderCreateDto;
import uz.pdp.pdp_food_delivery.rest.dto.mealorder.MealOrderDto;
import uz.pdp.pdp_food_delivery.rest.dto.mealorder.MealOrderUpdateDto;
import uz.pdp.pdp_food_delivery.rest.entity.AuthUser;
import uz.pdp.pdp_food_delivery.rest.entity.meal.Meal;
import uz.pdp.pdp_food_delivery.rest.entity.meal.MealOrder;
import uz.pdp.pdp_food_delivery.rest.mapper.mealorder.MealOrderMapper;
import uz.pdp.pdp_food_delivery.rest.repository.auth.AuthUserRepository;
import uz.pdp.pdp_food_delivery.rest.repository.meal.MealRepository;
import uz.pdp.pdp_food_delivery.rest.repository.mealorder.MealOrderRepository;
import uz.pdp.pdp_food_delivery.rest.service.base.AbstractService;
import uz.pdp.pdp_food_delivery.rest.service.base.GenericCrudService;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MealOrderService extends AbstractService<MealOrderMapper, MealOrderRepository>
        implements GenericCrudService<MealOrder, MealOrderDto, MealOrderCreateDto, MealOrderUpdateDto> {

    private final AuthUserRepository userRepository;
    private final MealRepository mealRepository;

    public MealOrderService(MealOrderMapper mapper, MealOrderRepository repository, AuthUserRepository userRepository, MealRepository mealRepository) {
        super(mapper, repository);
        this.userRepository = userRepository;
        this.mealRepository = mealRepository;
    }

    @Override
    public List<MealOrderDto> getAll() {

        List<MealOrder> orders = repository.findAll();
        return mapper.toDto(orders);
    }

    @Override
    public MealOrderDto get(Long id) {
        Optional<MealOrder> byId = repository.findById(id);

        if (byId.isPresent()) {
            MealOrder mealOrder = byId.get();
            MealOrderDto dto = mapper.toDto(mealOrder);
            return dto;
        } else {
            throw new RuntimeException("Order not found");
        }

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void update(MealOrderUpdateDto updateDto) {
        repository.updateMealOrderDone(updateDto.getChatId());
    }

    @Override
    public Long create(MealOrderCreateDto mealOrderCreateDto) {
        MealOrder mealOrder = mapper.fromCreateDto(mealOrderCreateDto);
        AuthUser authUser = userRepository.findById(mealOrderCreateDto.getUserDto().getId()).get();
        Meal meal = mealRepository.findById(mealOrderCreateDto.getMealDto().getId()).get();
        mealOrder.setUser(authUser);
        mealOrder.setMeal(meal);
        MealOrder save = repository.save(mealOrder);
        return save.getId();
    }


    public List<MealOrderDto> findOrderForExcelFile(LocalDateTime date) {

        Date sqlDate = Date.valueOf(date.toLocalDate());

        List<MealOrder> orders = repository.findByDate(sqlDate);
        List<MealOrderDto> dto =mapper.toDto(orders);

        return dto;
    }


}
