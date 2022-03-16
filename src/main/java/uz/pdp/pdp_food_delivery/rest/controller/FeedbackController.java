package uz.pdp.pdp_food_delivery.rest.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import uz.pdp.pdp_food_delivery.rest.controller.base.AbstractController;
import uz.pdp.pdp_food_delivery.rest.dto.feedback.FeedbackCreateDto;
import uz.pdp.pdp_food_delivery.rest.dto.feedback.FeedbackDto;
import uz.pdp.pdp_food_delivery.rest.dto.feedback.FeedbackUpdateDto;
import uz.pdp.pdp_food_delivery.rest.response.ResponseEntity;
import uz.pdp.pdp_food_delivery.rest.service.feedback.FeedbackService;

import java.util.List;


@RestController
@RequestMapping("/feedback")
public class FeedbackController extends AbstractController<FeedbackService> {

    public FeedbackController(FeedbackService service) {
        super(service);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<Long> create(@RequestBody FeedbackCreateDto dto) {
        Long aLong = service.create(dto);
        return new ResponseEntity<>(aLong);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<HttpStatus> update(@RequestBody FeedbackUpdateDto dto) {
        service.update(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<HttpStatus>  delete(@PathVariable("id") Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public ResponseEntity<FeedbackDto> get(@PathVariable("id") Long id) {
        FeedbackDto feedbackDto = service.get(id);
        return new ResponseEntity<>(feedbackDto);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity<List<FeedbackDto>> getAll() {
        List<FeedbackDto> feedbackDtos = service.getAll();
        return new ResponseEntity<>(feedbackDtos);
    }
}
