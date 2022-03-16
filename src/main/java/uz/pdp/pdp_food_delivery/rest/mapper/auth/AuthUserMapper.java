package uz.pdp.pdp_food_delivery.rest.mapper.auth;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;
import uz.pdp.pdp_food_delivery.rest.dto.auth.AuthUserCreateDto;
import uz.pdp.pdp_food_delivery.rest.dto.auth.AuthUserDto;
import uz.pdp.pdp_food_delivery.rest.dto.auth.AuthUserUpdateDto;
import uz.pdp.pdp_food_delivery.rest.entity.AuthUser;
import uz.pdp.pdp_food_delivery.rest.mapper.BaseMapper;
import java.util.List;


//@Component
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface  AuthUserMapper extends BaseMapper<
        AuthUser,
        AuthUserDto,
        AuthUserCreateDto,
        AuthUserUpdateDto> {

    AuthUserDto toDto(AuthUser user);

    List<AuthUserDto> toDto(List<AuthUser> users);

    AuthUser fromCreateDto(AuthUserCreateDto dto);

    AuthUser fromUpdateDto(AuthUserUpdateDto dto);

}
