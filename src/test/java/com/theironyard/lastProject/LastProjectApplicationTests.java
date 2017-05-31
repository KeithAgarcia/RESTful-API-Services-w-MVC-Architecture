package com.theironyard.lastProject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theironyard.lastProject.entities.Meal;
import com.theironyard.lastProject.repositories.MealRepository;
import com.theironyard.lastProject.repositories.ServingRepository;
import com.theironyard.lastProject.services.MealService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LastProjectApplicationTests {
	MealRepository meals;
	ServingRepository servings;
	MealService mealService;

	@Autowired
	WebApplicationContext wap;

	MockMvc mockMvc;

	@Before
	public void before() {
		// we're going to use our web application context to
		// build our mockmvc object.
		mockMvc = MockMvcBuilders.webAppContextSetup(wap).build();
	}

	@Test
	public void saveMeal() throws Exception{
		Meal meal = new Meal();

		meal.setId(1);
		meal.setName("pizza");
		meal.setAvailableTime(LocalDateTime.now());
		meal.setRecipe("Awesome Toppings");
		meal.setCategory("Italian");

		ObjectMapper mapper = new ObjectMapper();

		String json = mapper.writeValueAsString(meal);

		mockMvc.perform(
				MockMvcRequestBuilders.post("/create-meal")
						.content(json)
						.contentType("application/json")
		);

		Assert.assertTrue(meals.count() == 1);
	}


}

//	        this.servings = servings;
//        this.user = user;
//        this.name = name;
//        this.availableTime = availableTime;
//        this.recipe = recipe;
//        this.category = category;


