package uz.pdp.pdp_food_delivery.rest.mapper.dailymeal;


import org.mapstruct.Mapper;
import uz.pdp.pdp_food_delivery.rest.dto.dailymeal.DailyMealCreateDto;
import uz.pdp.pdp_food_delivery.rest.dto.dailymeal.DailyMealDto;
import uz.pdp.pdp_food_delivery.rest.dto.dailymeal.DailyMealUpdateDto;
import uz.pdp.pdp_food_delivery.rest.dto.meal.MealDto;
import uz.pdp.pdp_food_delivery.rest.entity.meal.DailyMeal;
import uz.pdp.pdp_food_delivery.rest.mapper.BaseMapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DailyMealMapper extends BaseMapper<
        DailyMeal,
        DailyMealDto,
        DailyMealCreateDto,
        DailyMealUpdateDto> {



    DailyMealDto toDto(DailyMeal dailyMeal);

    @Override
    List<DailyMealDto> toDto(List<DailyMeal> e);

    @Override
    DailyMeal fromCreateDto(DailyMealCreateDto dailyMealCreateDto);

    @Override
    DailyMeal fromUpdateDto(DailyMealUpdateDto dailyMealUpdateDto);

    List<MealDto> toMealDto(List<DailyMeal> dailyMeals);

}
