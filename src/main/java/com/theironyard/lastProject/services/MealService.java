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

        for (int i = 0; i < meal.getServingCount(); i++) {
            Serving serving = new Serving(meal);
            User user = users.findFirstByUsername(u.getUsername());
            serving.setUserEater(u);
            servings.save(serving);
            users.save(u);
        }

    }

    public void reserveServing(Meal meal, User u, Serving requestServing) {
        requestServing.setUserEater(u);
        requestServing.setMeal(meal);

        if (Integer.valueOf(requestServing.getServingAmt()) < requestServing.getUserEater().getToken()) {
            for (int i = 0; i < Integer.valueOf(requestServing.getServingAmt()); i++) {
                Serving serving = servings.findFirstByMealAndEtaIsNull(meal);
                User user = users.findFirstByUsername(u.getUsername());

                if (serving.getEta() == null) {
                    serving.setEta(requestServing.getEta());
                    meal.setServingCount(meal.getServingCount() - 1);
                    serving.setEta(requestServing.getEta());
                    serving.setUserEater(u);

                    users.save(u);
                    servings.save(serving);
                }
            }
        } else {
            throw new IllegalArgumentException("Not enough tokens");
        }
    }

    public void completeServing(User user, Meal meal){
        List <Serving> userServings= servings.findByUserAndMeal(user, meal);

        for(Serving s : userServings) {
            for(int i = 0; i < userServings.size(); i ++) {
                if (s.getComplete() == false) {
                    user.setToken(user.getToken() - 1);
                    s.getMeal().getUser().setToken(s.getMeal().getUser().getToken() + 1);
                    s.setComplete(true);
                }
            }
        }
        servings.save(userServings);
    }
}