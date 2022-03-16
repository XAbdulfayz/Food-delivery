package uz.pdp.pdp_food_delivery.rest.service.base;


import uz.pdp.pdp_food_delivery.rest.dto.GenericDto;

import java.util.List;


public interface GenericService<D extends GenericDto> extends BaseService {

    List<D> getAll();

    D get(Long id);

}
