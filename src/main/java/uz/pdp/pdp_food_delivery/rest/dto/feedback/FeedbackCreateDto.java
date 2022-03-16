package uz.pdp.pdp_food_delivery.rest.dto.feedback;

import lombok.Getter;
import lombok.Setter;
import uz.pdp.pdp_food_delivery.rest.dto.BaseDto;
import uz.pdp.pdp_food_delivery.rest.enums.FeedbackType;

@Getter
@Setter
public class FeedbackCreateDto implements BaseDto {

    private String message;

    private Long  user;

    private FeedbackType feedbackType;

    public FeedbackCreateDto() {
    }

    public FeedbackCreateDto(String message, Long user) {
        this.message = message;
        this.user = user;
        this.feedbackType = FeedbackType.ADVICE;
    }
}
