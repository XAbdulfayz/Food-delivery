package uz.pdp.pdp_food_delivery.rest.entity.meal;

import com.sun.xml.bind.v2.schemagen.xmlschema.List;
import lombok.Getter;
import lombok.Setter;
import uz.pdp.pdp_food_delivery.rest.entity.AuthUser;
import uz.pdp.pdp_food_delivery.rest.entity.base.Auditable;
import uz.pdp.pdp_food_delivery.rest.entity.meal.Meal;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "meal_order", schema = "meal_order")
public class MealOrder extends Auditable {

    @OneToOne
    private AuthUser user;

    @OneToOne
    private Meal meal;

    @Column(columnDefinition = "boolean default false")
    private boolean done;

}
