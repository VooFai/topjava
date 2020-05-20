package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    @Override
    @Transactional
    <S extends Meal> S save(S entity);

    @Query(Meal.DELETE)
    @Modifying
    @Transactional
    int delete(@Param("id") Integer id, @Param("user_id") Integer userId);

    @Query(Meal.GET_BY_ID_AND_USER)
    Meal get(@Param("id") Integer id, @Param("user_id") Integer userId);

    @Query(Meal.GET_ALL_SORTED)
    List<Meal> getAll(@Param("user_id") int userId);

    @Query(Meal.GET_BETWEEN_DATES)
    List<Meal> getBetween(@Param("start_date") LocalDateTime startDate,
                          @Param("end_date") LocalDateTime endDate,
                          @Param("user_id") int userId);
}
