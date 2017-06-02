package com.theironyard.lastProject.services;

import com.theironyard.lastProject.entities.Meal;
import com.theironyard.lastProject.entities.Serving;
import com.theironyard.lastProject.entities.User;
import com.theironyard.lastProject.repositories.MealRepository;
import com.theironyard.lastProject.repositories.ServingRepository;
import org.springframework.stereotype.Service;

/**
 * Created by Keith on 5/31/17.
 */
@Service
public class MealService {
    MealRepository meals;
    ServingRepository servings;

    public MealService(MealRepository mealRepository, ServingRepository servings) {
        this.meals = mealRepository;
        this.servings = servings;
    }

    public void saveMeal(Meal meal, User u) {
        meal.setUser(u);
        meals.save(meal);

        for(int i = 0; i < meal.getServingCount(); i++){
            Serving serving = new Serving(meal);
            servings.save(serving);
        }

    }

    public void reserveServing(Meal meal, User u, Serving requestServing) {
        requestServing.setUserEater(u);
        requestServing.setMeal(meal);

        // see if there are any servings in this meal without an eta (e.g. not taken
        Serving serving = servings.findFirstByMealAndEtaIsNull(meal);

        // if there is a serving that matches your criteria,
        if(serving!= null) {
            // update it with requestServing's eta.
            serving.setEta(requestServing.getEta());

            serving.setUserEater(u);

            // save the existing serving.
            servings.save(serving);
        }
    }

}