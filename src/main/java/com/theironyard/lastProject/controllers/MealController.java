package com.theironyard.lastProject.controllers;

import com.theironyard.lastProject.entities.Meal;
import com.theironyard.lastProject.entities.User;
import com.theironyard.lastProject.repositories.MealRepository;
import com.theironyard.lastProject.repositories.UserRepository;
import com.theironyard.lastProject.services.MealService;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Keith on 5/31/17.
 */
@RestController
public class MealController {
    MealRepository meals;
    MealService mealService;
    UserRepository users;

    public MealController(MealService mealService, UserRepository users) {
        this.users = users;
        this.mealService = mealService;
    }

    @CrossOrigin
    @RequestMapping(path = "/new-meal", method = RequestMethod.POST) //User user or Meal meal?
    public void saveMeal(@RequestBody Meal meal){ //@requestbody?? //save meal = method name

        User u = users.findOne(1);
        mealService.saveMeal(meal, u);
    }

    @CrossOrigin
    @RequestMapping(path = "/select-meal/{id}", method = RequestMethod.GET)
    public Meal getMeal(@PathVariable("id") int id) {
        return meals.findOne(id);
    }
}
