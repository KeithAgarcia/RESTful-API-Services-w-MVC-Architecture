package com.theironyard.lastProject.repositories;

import com.theironyard.lastProject.entities.Meal;
import com.theironyard.lastProject.entities.Serving;
import com.theironyard.lastProject.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Keith on 5/31/17.
 */
public interface ServingRepository extends CrudRepository<Serving, Integer> {

    // finds the first serving by meal AND eta is null
    Serving findFirstByMealAndEtaIsNull(Meal m);

    // finds the first serving by meal AND eta is a specific value
    Serving findFirstByMealAndEta(Meal m, LocalDateTime eta);

    List<Serving> findByUserAndMeal(User user, Meal m);

}
