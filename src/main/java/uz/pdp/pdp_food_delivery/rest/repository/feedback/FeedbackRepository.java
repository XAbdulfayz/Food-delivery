package uz.pdp.pdp_food_delivery.rest.repository.feedback;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.pdp.pdp_food_delivery.rest.entity.Feedback;
import uz.pdp.pdp_food_delivery.rest.repository.BaseRepository;

import javax.transaction.Transactional;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long>, BaseRepository {


    @Transactional
    @Modifying
    @Query(value = "update feedback.feedback f set f.message = :#{#dto.message}, f.feedbackType = :#{#dto.feedbackType}}", nativeQuery = true)
    void update(@Param(value = "dto") Feedback feedback);


}
