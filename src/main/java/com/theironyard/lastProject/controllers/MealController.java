package com.theironyard.lastProject.controllers;

import com.theironyard.lastProject.entities.Meal;
import com.theironyard.lastProject.entities.Serving;
import com.theironyard.lastProject.entities.User;
import com.theironyard.lastProject.repositories.MealRepository;
import com.theironyard.lastProject.repositories.ServingRepository;
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
    ServingRepository servings;

    public MealController(MealRepository meals, MealService mealService, UserRepository users, ServingRepository servings) {
        this.users = users;
        this.mealService = mealService;
        this.meals = meals;
        this.servings = servings;
    }

    @CrossOrigin
    @RequestMapping(path = "/new-meal", method = RequestMethod.POST)
    public  Meal saveMeal(@RequestBody Meal meal){
        User u = users.findOne(meal.getUser().getId());
        mealService.saveMeal(meal, u);
        return meal;
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
    @RequestMapping(path = "/reserve-serving/{id}", method = RequestMethod.PUT)
    public Meal reserveServing(@RequestBody Serving serving, @PathVariable ("id") int id, User user){
        User u = users.findOne(user.getId());
        Meal m = meals.findOne(id);
        mealService.reserveServing(m, u, serving);

       return serving.getMeal();
    }


    @CrossOrigin
    @RequestMapping(path = "/user-meals/{id}", method = RequestMethod.GET)
    public List<Meal> userMeals( @PathVariable("id")  User user) {
        return (List<Meal>) meals.findAllByUser(user);
    }

    @CrossOrigin
    @RequestMapping(path = "/update-complete/{id}", method = RequestMethod.PUT)
    public void confirmMeal(@PathVariable("id") int id, User user){
        User u = users.findOne(user.getId());
        Meal m = meals.findOne(id);
        mealService.completeServing(u, m);
    }

    @CrossOrigin
    @RequestMapping(path = "/meals-complete/{id}", method = RequestMethod.GET)
    public List<Meal> completeMeals(@PathVariable("id") int id){
        User u = users.findOne(id);
        List<Meal> servingMeals = meals.findDistinctByServings(u);
        return servingMeals;
    }


}
