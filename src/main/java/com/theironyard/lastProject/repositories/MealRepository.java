package com.theironyard.lastProject.repositories;

import com.theironyard.lastProject.entities.Meal;
import com.theironyard.lastProject.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 *
 * Created by Keith on 5/31/17.
 */
public interface MealRepository extends CrudRepository <Meal, Integer> {
    List<Meal> findAllByUser(User user);

    @Query("select distinct m from Serving s inner join s.meal m where s.user = ?1 and s.complete = false")
    List<Meal> findDistinctByServings(User user);


}
