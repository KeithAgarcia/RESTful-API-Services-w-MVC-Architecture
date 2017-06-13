package com.theironyard.lastProject.services;

import com.theironyard.lastProject.entities.Meal;
import com.theironyard.lastProject.entities.Serving;
import com.theironyard.lastProject.entities.User;
import com.theironyard.lastProject.repositories.MealRepository;
import com.theironyard.lastProject.repositories.ServingRepository;
import com.theironyard.lastProject.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        meals.save(meal);

        for (int i = 0; i < meal.getServingCount(); i++) {
            Serving serving = new Serving(meal);
            serving.setEatAmt(0);
            serving.setEatAmt(serving.getEatAmt() + 1);
            meal.getUser().setToken(meal.getUser().getToken() + 1);

            servings.save(serving);

        }

    }

    public void reserveServing(Meal meal, User u, Serving requestServing) {
        requestServing.setUserEater(u);
        requestServing.setMeal(meal);

        if (Integer.valueOf(requestServing.getServingAmt()) <= requestServing.getUserEater().getToken()) {
            for (int i = 0; i < Integer.valueOf(requestServing.getServingAmt()); i++) {
                Serving serving = servings.findFirstByMealAndEtaIsNull(meal);
                List <Serving> requestedAmt = new ArrayList<>();
//                User user = users.findFirstByUsername(u.getUsername());

                if (serving.getEta() == null) {
                    serving.setEta(requestServing.getEta());
                    meal.setServingCount(meal.getServingCount() - 1);
                    serving.setEta(requestServing.getEta());
                    meal.getUser().setTotalCookMeals(meal.getUser().getTotalRatings() + 1);
                    serving.setUserEater(u);
                    serving.getUserEater().setToken(serving.getUserEater().getToken() - 1);
                    serving.setEatAmt(Integer.valueOf(requestServing.getServingAmt()));

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
                    s.getMeal().getUser().setToken(s.getMeal().getUser().getToken() + 1);
                    s.setComplete(true);
                }
            }
        }
        servings.save(userServings);
    }

    public int getServingCount (Meal meal, User user){
        List <Serving> userServings= servings.findByUserAndMeal(user, meal);
        return userServings.size();

    }
}