package uz.pdp.pdp_food_delivery.rest.mapper.meal;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;
import uz.pdp.pdp_food_delivery.rest.dto.meal.MealCreateDto;
import uz.pdp.pdp_food_delivery.rest.dto.meal.MealDto;
import uz.pdp.pdp_food_delivery.rest.dto.meal.MealUpdateDto;
import uz.pdp.pdp_food_delivery.rest.entity.meal.Meal;
import uz.pdp.pdp_food_delivery.rest.mapper.BaseMapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MealMapper extends BaseMapper<
        Meal,
        MealDto,
        MealCreateDto,
        MealUpdateDto
        > {


    List<MealDto> toDto(List<Meal> meals);

    @Mapping(target = "picture", ignore = true)
    Meal fromCreateDto(MealCreateDto mealCreateDto);

    MealDto toDto(Meal meal);

    @Override
    @Mapping(target = "picture", ignore = true)
    Meal fromUpdateDto(MealUpdateDto mealUpdateDto);

    @Mapping(target = "picture", ignore = true)
    void fromUpdateDto(MealUpdateDto mealUpdateDto, @MappingTarget Meal meal);

}
