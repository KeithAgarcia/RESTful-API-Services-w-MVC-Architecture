package com.theironyard.lastProject.repositories;

import com.theironyard.lastProject.entities.Meal;
import com.theironyard.lastProject.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 *
 * Created by Keith on 5/31/17.
 */
public interface MealRepository extends CrudRepository <Meal, Integer> {
    List<Meal> findAllByUser(User user);
}
