package uz.pdp.pdp_food_delivery.rest.entity;


import lombok.Getter;
import lombok.Setter;
import uz.pdp.pdp_food_delivery.rest.entity.base.Auditable;
import uz.pdp.pdp_food_delivery.rest.enums.FeedbackType;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "feedback", schema = "feedback")
public class Feedback extends Auditable {

    private String message;

    @OneToOne
    private AuthUser user;

    @Enumerated(EnumType.STRING)
    private FeedbackType feedbackType;

}
