package uz.pdp.pdp_food_delivery.rest.controller;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import uz.pdp.pdp_food_delivery.rest.controller.base.AbstractController;
import uz.pdp.pdp_food_delivery.rest.dto.dailymeal.DailyMealCreateDto;
import uz.pdp.pdp_food_delivery.rest.dto.dailymeal.DailyMealDto;
import uz.pdp.pdp_food_delivery.rest.dto.dailymeal.DailyMealUpdateDto;
import uz.pdp.pdp_food_delivery.rest.response.ResponseEntity;
import uz.pdp.pdp_food_delivery.rest.service.dailymeal.DailyMealService;

import java.util.List;

@RestController
@RequestMapping(value = "/daily-meal")
public class DailyMealController extends AbstractController<DailyMealService> {


    public DailyMealController(DailyMealService service) {
        super(service);
    }

//    @RequestMapping(value = "/truncate",method = RequestMethod.DELETE)
//    public ResponseEntity<HttpStatus>  truncate() {
//        service.truncate();
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<Long> create(@RequestBody DailyMealCreateDto dto) {
        Long aLong = service.create(dto);
        return new ResponseEntity<>(aLong);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<HttpStatus> update(@RequestBody DailyMealUpdateDto dto) {
        service.update(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<HttpStatus>  delete(@PathVariable("id") Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public ResponseEntity<DailyMealDto> get(@PathVariable("id") Long id) {
        DailyMealDto dailyMealDto = service.get(id);
        return new ResponseEntity<>(dailyMealDto);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity<List<DailyMealDto>> getAll() {
        List<DailyMealDto> dailyMealDtos = service.getAll();
        return new ResponseEntity<>(dailyMealDtos);
    }


}
