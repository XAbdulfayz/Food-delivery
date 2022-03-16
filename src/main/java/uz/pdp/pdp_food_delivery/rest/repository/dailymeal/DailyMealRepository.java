package uz.pdp.pdp_food_delivery.rest.repository.dailymeal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.pdp.pdp_food_delivery.rest.entity.meal.DailyMeal;
import uz.pdp.pdp_food_delivery.rest.repository.BaseRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DailyMealRepository extends JpaRepository<DailyMeal, Long>, BaseRepository {

    @Query(value = "select d.name from daily_meal.daily_meal d", nativeQuery = true)
    List<String> getAllName();


    @Query(value = "truncate table daily_meal.daily_meal",nativeQuery = true)
    void truncate();


    @Query(value = "select d.name from daily_meal.daily_meal d where not deleted and d.name ilike name limit 1", nativeQuery = true)
    Optional<DailyMeal> findByName(@Param(value = "name") String name);
}
