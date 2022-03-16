package uz.pdp.pdp_food_delivery.rest.controller;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import uz.pdp.pdp_food_delivery.rest.controller.base.AbstractController;
import uz.pdp.pdp_food_delivery.rest.dto.excelFile.ExcelFileDto;
import uz.pdp.pdp_food_delivery.rest.dto.mealorder.MealOrderCreateDto;
import uz.pdp.pdp_food_delivery.rest.dto.mealorder.MealOrderDto;
import uz.pdp.pdp_food_delivery.rest.response.ResponseEntity;
import uz.pdp.pdp_food_delivery.rest.service.excelFile.ExcelFileService;
import uz.pdp.pdp_food_delivery.rest.service.mealorder.MealOrderService;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/mealOrder")
public class MealOrderController extends AbstractController<MealOrderService> {

    private final ExcelFileService fileService;

    public MealOrderController(MealOrderService service, ExcelFileService fileService) {
        super(service);
        this.fileService = fileService;
    }


    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<Long> createOrder(@RequestBody MealOrderCreateDto dto){
        Long id = service.create(dto);
        return new ResponseEntity<>(id);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResponseEntity<List<MealOrderDto>> getAll(@RequestBody LocalDateTime date){
        List<MealOrderDto> orders = service.findOrderForExcelFile(date);
        return new ResponseEntity<>(orders);
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ResponseEntity<MealOrderDto> getById(@RequestParam Long id){
        MealOrderDto order = service.get(id);
        return new ResponseEntity<>(order);
    }

    @RequestMapping(value = "/getExcelFile")
    public void getExcelFile(@RequestBody LocalDateTime date, HttpServletResponse response) throws IOException {
        ExcelFileDto excelFileDto = fileService.getExcelFile(date);


        response.setHeader("Content-Disposition", "attachment; filename=\"" + "Orders" + "\"");
        response.setContentType(excelFileDto.getMimeType());
        FileInputStream fileInputStream = new FileInputStream("src/main/resources/excelFileStorage/" + excelFileDto.getName());

        FileCopyUtils.copy(fileInputStream, response.getOutputStream());

    }





}
