package com.theironyard.lastProject.services;

import com.theironyard.lastProject.entities.Meal;
import com.theironyard.lastProject.entities.Serving;
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
//Do I need to findFirstByName or ID?
//    public void createMeal(String name, boolean cook, String recipe, String pickup, String eta, String category, int servingAmt) {
//            Meal meal = new Meal(name, cook, recipe, pickup, eta, category);
//            meals.save(meal);
//
//            for(int i = 0; i < servingAmt; i++) {
//                Serving serving = new Serving(meal);
//
//                servings.save(serving);
//            }
//        }


    public void saveMeal(Meal meal) {
        meals.save(meal);

        for(int i = 0; i < meal.getServingCount(); i++){
            Serving serving = new Serving(meal);
            servings.save(serving);
        }
    }
}