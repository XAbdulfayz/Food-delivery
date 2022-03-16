package uz.pdp.pdp_food_delivery.rest.service.base;

import lombok.NoArgsConstructor;
import uz.pdp.pdp_food_delivery.rest.mapper.Mapper;
import uz.pdp.pdp_food_delivery.rest.repository.BaseRepository;
public abstract class AbstractService<M extends Mapper, R extends BaseRepository> {

    protected final M mapper;
    protected final R repository;


    public AbstractService(M mapper, R repository) {
        this.mapper = mapper;
        this.repository = repository;
    }




}
