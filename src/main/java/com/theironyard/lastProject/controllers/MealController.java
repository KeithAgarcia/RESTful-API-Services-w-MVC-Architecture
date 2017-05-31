package com.theironyard.lastProject.controllers;

import com.theironyard.lastProject.entities.Meal;
import com.theironyard.lastProject.services.MealService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Keith on 5/31/17.
 */
@RestController
public class MealController {
    MealService mealService;

    public MealController(MealService mealService) {
        this.mealService = mealService;
    }

//    @RequestMapping(path = "/new-meal", method = RequestMethod.POST) //User user or Meal meal?
//    public String newMeal(Meal meal, int servings){ //@requestbody?? //save meal = method name
//        mealService.saveMeal(meal, servings);
////        mealService.createMeal(meal.getName(),meal.isCook(), meal.getRecipe(), meal.getPickup(), meal.getEta(), meal.getCategory(), meal.getServings());
//        return "/";
//    }

    @RequestMapping(path = "/new-meal", method = RequestMethod.POST) //User user or Meal meal?
    public String saveMeal(Meal meal, int servings){ //@requestbody?? //save meal = method name
        mealService.saveMeal(meal, servings);
        return ":/";
    }
}
