package uz.pdp.pdp_food_delivery.rest.entity.meal;

import lombok.Getter;
import lombok.Setter;
import uz.pdp.pdp_food_delivery.rest.entity.base.Auditable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "daily_meal", schema = "daily_meal")
public class DailyMeal extends Auditable {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate date;

    @Column(name = "photo_id", nullable = true)
    private String photoId;
}
