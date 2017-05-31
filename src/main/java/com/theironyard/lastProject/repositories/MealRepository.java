package com.theironyard.lastProject.repositories;

import com.theironyard.lastProject.entities.Meal;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Keith on 5/31/17.
 */
public interface MealRepository extends CrudRepository <Meal, Integer> {
}
