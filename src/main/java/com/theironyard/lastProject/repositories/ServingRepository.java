package com.theironyard.lastProject.repositories;

import com.theironyard.lastProject.entities.Meal;
import com.theironyard.lastProject.entities.Serving;
import com.theironyard.lastProject.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Keith on 5/31/17.
 */
public interface ServingRepository extends CrudRepository<Serving, Integer> {
    Serving findFirstByMealAndEtaIsNull(Meal m);
    List<Serving> findByUserAndMeal(User user, Meal m);
    List<Serving> findAllByUserAndMealAndCompleteIsNull(User user, Meal m);
    List<Serving> findByUser(User u);

}
