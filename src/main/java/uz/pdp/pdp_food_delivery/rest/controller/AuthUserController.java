package uz.pdp.pdp_food_delivery.rest.controller;

import org.springframework.web.bind.annotation.*;
import uz.pdp.pdp_food_delivery.rest.controller.base.AbstractController;
import uz.pdp.pdp_food_delivery.rest.dto.auth.AuthUserCreateDto;
import uz.pdp.pdp_food_delivery.rest.dto.auth.AuthUserDto;
import uz.pdp.pdp_food_delivery.rest.dto.auth.AuthUserUpdateDto;
import uz.pdp.pdp_food_delivery.rest.response.Data;
import uz.pdp.pdp_food_delivery.rest.response.ResponseEntity;
import uz.pdp.pdp_food_delivery.rest.service.auth.AuthUserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/auth/")
public class AuthUserController extends AbstractController<AuthUserService> {

    public AuthUserController(AuthUserService service) {
        super(service);
    }


    //Admin uchun
    @GetMapping("list")
    public ResponseEntity<Data<List<AuthUserDto>>> getGivenPageAndSizeOfUsers(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                                              @RequestParam(value = "size", defaultValue = "10") Integer size) {
        List<AuthUserDto> all = service.getGivenPage(page, size);
        return new ResponseEntity<>(new Data<>(all.size(), all));
    }

    @GetMapping("get/{id}")
    public ResponseEntity<Data<AuthUserDto>> getById(@PathVariable Long id) {
        try {
            AuthUserDto authUserDto = service.get(id);
            return new ResponseEntity<>(new Data<>(1, authUserDto));
        } catch (RuntimeException ignored) {
            return null;
        }
    }

    @PostMapping("create")
    public ResponseEntity<Long> create(@RequestBody AuthUserCreateDto dto, @RequestParam(defaultValue = "null") Long userId) {
        Long aLong = service.create(dto, userId);
        return new ResponseEntity<>(aLong);
    }

    @PatchMapping("update")
    public ResponseEntity<String> update(@RequestBody AuthUserUpdateDto dto) {
        service.update(dto);
        return new ResponseEntity<>("Success");
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity<>("Success");
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        service.getRefreshToken(request, response);
    }

}
