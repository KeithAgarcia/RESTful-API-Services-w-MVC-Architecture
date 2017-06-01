package com.theironyard.lastProject.controllers;

import com.theironyard.lastProject.entities.Meal;
import com.theironyard.lastProject.entities.Serving;
import com.theironyard.lastProject.entities.User;
import com.theironyard.lastProject.repositories.MealRepository;
import com.theironyard.lastProject.repositories.UserRepository;
import com.theironyard.lastProject.services.MealService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Keith on 5/31/17.
 */
//heroku pg:psql

@RestController
public class MealController {
    MealRepository meals;
    MealService mealService;
    UserRepository users;

    public MealController(MealRepository meals, MealService mealService, UserRepository users) {
        this.users = users;
        this.mealService = mealService;
        this.meals = meals;
    }

    @CrossOrigin
    @RequestMapping(path = "/new-meal", method = RequestMethod.POST)
    public void saveMeal(@RequestBody Meal meal){
        User u = users.findOne(1);
        mealService.saveMeal(meal, u);
    }

    @CrossOrigin
    @RequestMapping(path = "/select-meal/{id}", method = RequestMethod.GET)
    public Meal getMeal(@PathVariable("id") int id) {
        return meals.findOne(id);
    }

    @CrossOrigin
    @RequestMapping(path = "/all-meals", method = RequestMethod.GET)
    public List<Meal> getAllMeals() {
        return (List<Meal>) meals.findAll();
    }

    @CrossOrigin
    @RequestMapping(path = "/reserve-serving", method = RequestMethod.PUT)
    public void reserveServing(@RequestBody Serving serving){
        User u = users.findOne(1);
        mealService.reserveServing(serving, u);
    }
}
