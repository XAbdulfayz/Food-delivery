package uz.pdp.pdp_food_delivery.rest.service.feedback;

import org.springframework.stereotype.Service;
import uz.pdp.pdp_food_delivery.rest.dto.feedback.FeedbackCreateDto;
import uz.pdp.pdp_food_delivery.rest.dto.feedback.FeedbackDto;
import uz.pdp.pdp_food_delivery.rest.dto.feedback.FeedbackUpdateDto;
import uz.pdp.pdp_food_delivery.rest.entity.Feedback;
import uz.pdp.pdp_food_delivery.rest.mapper.feedback.FeedbackMapper;
import uz.pdp.pdp_food_delivery.rest.repository.feedback.FeedbackRepository;
import uz.pdp.pdp_food_delivery.rest.service.base.AbstractService;
import uz.pdp.pdp_food_delivery.rest.service.base.GenericCrudService;

import java.util.List;
@Service
public class  FeedbackService extends AbstractService<FeedbackMapper, FeedbackRepository>
        implements GenericCrudService<Feedback, FeedbackDto, FeedbackCreateDto, FeedbackUpdateDto> {
    public FeedbackService(FeedbackMapper mapper, FeedbackRepository repository) {
        super(mapper, repository);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void update(FeedbackUpdateDto feedbackUpdateDto) {
        Feedback feedback = mapper.fromUpdateDto(feedbackUpdateDto);
        repository.update(feedback);
    }

    @Override
    public Long create(FeedbackCreateDto feedbackCreateDto) {
        Feedback feedback = mapper.fromCreateDto(feedbackCreateDto);
        repository.save(feedback);
        return feedback.getId();
    }

    @Override
    public List<FeedbackDto> getAll() {
        return mapper.toDto(repository.findAll());
    }

    @Override
    public FeedbackDto get(Long id) {
        return mapper.toDto(repository.findById(id).get());
    }
}
