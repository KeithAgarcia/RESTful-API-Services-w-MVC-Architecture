package com.theironyard.lastProject.controllers;

import com.theironyard.lastProject.entities.Meal;
import com.theironyard.lastProject.entities.Serving;
import com.theironyard.lastProject.entities.User;
import com.theironyard.lastProject.repositories.MealRepository;
import com.theironyard.lastProject.repositories.ServingRepository;
import com.theironyard.lastProject.repositories.UserRepository;
import com.theironyard.lastProject.services.MealService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
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
        org.springframework.security.core.userdetails.User auth = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User u = users.findFirstByUsername(auth.getUsername());
        meal.setUser(u);
        meal.getUser().setTotalCookMeals(meal.getUser().getTotalCookMeals() + 1);
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
        List<Meal> mealsList = (List<Meal>) meals.findAll();
        List<Meal> othersMeals = new ArrayList<>();
        org.springframework.security.core.userdetails.User auth = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User u = users.findFirstByUsername(auth.getUsername());

        for(Meal meal : mealsList){
            if(meal.getUser() != u){
                if(meal.getServingCount() > 0) {
                    othersMeals.add(meal);
                }
            }
        }
        return othersMeals;
    }

    @CrossOrigin
    @RequestMapping(path = "/get-servings/{meal_id}", method = RequestMethod.GET)   //{user_id}
    public int getServings(@PathVariable("meal_id") int mealId) { //@PathVariable("user_id") int userId)
        org.springframework.security.core.userdetails.User auth = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User u = users.findFirstByUsername(auth.getUsername());
        Meal m = meals.findOne(mealId);

        return mealService.getServingCount(m, u);
    }
    @CrossOrigin
    @RequestMapping(path = "/reserve-serving/{id}", method = RequestMethod.PUT)
    public Meal reserveServing(@RequestBody Serving serving, @PathVariable ("id") int id, HttpServletResponse response){
        org.springframework.security.core.userdetails.User auth = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User u = users.findFirstByUsername(auth.getUsername());
        serving.setUserEater(u);
        Meal m = meals.findOne(id);

        try{
            mealService.reserveServing(m, u, serving);
            return serving.getMeal();

        } catch (IllegalArgumentException ex) {
            response.setStatus(422);
    }
            return serving.getMeal();
    }


    @CrossOrigin
    @RequestMapping(path = "/user-meals/{id}", method = RequestMethod.GET)
    public List<Meal> userMeals( @PathVariable("id")  User user) {
        return (List<Meal>) meals.findAllByUser(user);
    }

    @CrossOrigin
    @RequestMapping(path = "/update-complete/{id}", method = RequestMethod.PUT)
    public void confirmMeal(@PathVariable("id") int id, Serving serving){
        org.springframework.security.core.userdetails.User auth = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User u = users.findFirstByUsername(auth.getUsername());
        serving.setUserEater(u);
        Meal m = meals.findOne(id);
        mealService.completeServing(u, m);
    }


    @CrossOrigin
    @RequestMapping(path = "/meals-pending-eat", method = RequestMethod.GET)
    public List<Meal> incompleteMealsEat(){
        org.springframework.security.core.userdetails.User auth = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User u = users.findFirstByUsername(auth.getUsername());
        List<Meal> servingMeals = meals.findDistinctByServings(u);

        return servingMeals;
    }

    @CrossOrigin
    @RequestMapping(path = "/meals-pending-cook", method = RequestMethod.GET)
    public List <Meal> incompleteMealsCook() {
        org.springframework.security.core.userdetails.User auth = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User u = users.findFirstByUsername(auth.getUsername());
        List<Serving> cookedServings = new ArrayList<>();
        List<Serving> servingList = (List<Serving>) servings.findAll();
        List<Meal> cookedMeals = new ArrayList<>();
        for (Serving s : servingList) {
            if (s.getMeal().getUser() == u) {
                if (s.getComplete().equals(false)) { //&& s.getEta() == null
                    cookedServings.add(s);
                }
            }
        }
        for (Serving c : cookedServings) {
            if (cookedMeals.isEmpty()) {
                cookedMeals.add(c.getMeal());
            }
            if (!(cookedMeals.contains(c.getMeal()))) {
                cookedMeals.add(c.getMeal());
            }
        }
        return cookedMeals;
    }

    @CrossOrigin
    @RequestMapping(path = "/people-pickup", method = RequestMethod.GET)
    public List<Serving> peoplePickup(){
        org.springframework.security.core.userdetails.User auth = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User u = users.findFirstByUsername(auth.getUsername());
        List<Serving> cookedServings = new ArrayList<>();
        List<Serving> servingList = (List<Serving>) servings.findAll();

        for(Serving s : servingList){
            if(s.getMeal().getUser() == u){
                if(s.getComplete().equals(false) && s.getEta() != null){
                    cookedServings.add(s);
                }
            }
        }
        return cookedServings;
    }
}
