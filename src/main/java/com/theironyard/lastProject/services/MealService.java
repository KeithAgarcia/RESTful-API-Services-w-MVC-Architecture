package com.theironyard.lastProject.services;

import com.theironyard.lastProject.entities.Meal;
import com.theironyard.lastProject.entities.Serving;
import com.theironyard.lastProject.entities.User;
import com.theironyard.lastProject.repositories.MealRepository;
import com.theironyard.lastProject.repositories.ServingRepository;
import com.theironyard.lastProject.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Keith on 5/31/17.
 */
@Service
public class MealService {
    MealRepository meals;
    ServingRepository servings;
    UserRepository users;

    public MealService(MealRepository mealRepository, ServingRepository servings, UserRepository users) {
        this.meals = mealRepository;
        this.servings = servings;
        this.users = users;
    }

    public void saveMeal(Meal meal, User u) {
        meal.setUser(u);
        meals.save(meal);

        for(int i = 0; i < meal.getServingCount(); i++){
            Serving serving = new Serving(meal);
            User user = users.findFirstByUsername(u.getUsername());
            user.setToken(user.getToken() + 1);
            servings.save(serving);
            users.save(u);
        }

    }

    public void reserveServing(Meal meal, User u, Serving requestServing) {
        requestServing.setUserEater(u);
        requestServing.setMeal(meal);

        // see if there are any servings in this meal without an eta (e.g. not taken
        Serving serving = servings.findFirstByMealAndEtaIsNull(meal);
        User user = users.findFirstByUsername(u.getUsername());

        // if there is a serving that matches your criteria,
        if(serving!= null) {
            // update it with requestServing's eta.
            serving.setEta(requestServing.getEta());
            user.setToken(user.getToken() - 1);
            meal.setServingCount(meal.getServingCount() - 1);

            serving.setUserEater(u);

            // save the existing serving.
            users.save(u);
            servings.save(serving);
        }
    }

    public void completeServing(User user, Meal meal){
        List <Serving> userServings= servings.findByUserAndMeal(user, meal);
        for(Serving s : userServings) {
            s.setComplete(true);
        }
        servings.save(userServings);
    }
}