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

    public void reserveServing(Serving serving, User u, Meal m){
        serving.setUserEater(u);
        serving.setMeal(m);
        if(serving.getEta() == null) {
            servings.save(serving);
        }
    }
}