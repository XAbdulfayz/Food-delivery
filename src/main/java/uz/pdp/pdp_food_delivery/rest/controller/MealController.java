package uz.pdp.pdp_food_delivery.rest.controller;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import uz.pdp.pdp_food_delivery.rest.controller.base.AbstractController;
import uz.pdp.pdp_food_delivery.rest.dto.meal.MealCreateDto;
import uz.pdp.pdp_food_delivery.rest.dto.meal.MealDto;
import uz.pdp.pdp_food_delivery.rest.dto.meal.MealUpdateDto;
import uz.pdp.pdp_food_delivery.rest.response.ResponseEntity;
import uz.pdp.pdp_food_delivery.rest.service.meal.MealService;

import java.util.List;

@RestController
@RequestMapping("/meal/")
public class
MealController extends AbstractController<MealService> {

    public MealController(MealService service) {
        super(service);
    }

    @GetMapping(value = "get/{id}")
    public ResponseEntity<MealDto> get(@PathVariable Long id) {
        MealDto mealDto = service.get(id);
        return new ResponseEntity<>(mealDto);
    }

    @GetMapping(value = "list")
    public ResponseEntity<List<MealDto>> getAll(){
        List<MealDto> meals = service.getAll();
        return new ResponseEntity<>(meals);
    }

    @PostMapping(value = "create")
    public ResponseEntity<Long> create(@ModelAttribute MealCreateDto dto, @RequestParam(defaultValue = "-1") Long sessionUserId) {
        Long aLong = service.create(dto, sessionUserId);
        return new ResponseEntity<>(aLong);
    }

    @PatchMapping(value = "update/{id}")
    public void create(@ModelAttribute MealUpdateDto dto,@PathVariable(name = "id") Long id ,@RequestParam(defaultValue = "-1") Long sessionUserId) {
        dto.setId(id);
        service.update(dto, sessionUserId);
    }

    @DeleteMapping(value = "delete/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id, @RequestParam(defaultValue = "-1") Long sessionUserId) {
        service.delete(id, sessionUserId);
        return new ResponseEntity<>(org.springframework.http.HttpStatus.OK);
    }
}
