package com.theironyard.lastProject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theironyard.lastProject.entities.Meal;
import com.theironyard.lastProject.entities.Serving;
import com.theironyard.lastProject.entities.User;
import com.theironyard.lastProject.repositories.MealRepository;
import com.theironyard.lastProject.repositories.ServingRepository;
import com.theironyard.lastProject.repositories.UserRepository;
import com.theironyard.lastProject.services.MealService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LastProjectApplicationTests {
	@Autowired
	ObjectMapper mapper;

	@Autowired
	MealRepository meals;

	@Autowired
	ServingRepository servings;

	@Autowired
	UserRepository users;

	@Autowired
	MealService mealService;

	@Autowired
	WebApplicationContext wap;

	MockMvc mockMvc;

	@Before
	public void before() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wap).build();
        User user = new User();
        user.setUsername("Billy");
        users.save(user);

        Meal meal = new Meal();

        meal.setUser(user);
        meal.setName("pizza");
        meal.setAvailableTime(LocalDateTime.now());
        meal.setRecipe("Awesome Toppings");
        meal.setCategory("Italian");
        meal.setServingCount(5);

		// insert test meal into the db?
	}

	@Test
	public void saveMeal() throws Exception{
		User user = new User();
		user.setUsername("Billy");
		users.save(user);

		Meal meal = new Meal();

		meal.setUser(user);
		meal.setName("pizza");
		meal.setAvailableTime(LocalDateTime.now());
		meal.setRecipe("Awesome Toppings");
		meal.setCategory("Italian");
        meal.setServingCount(5);

		String json = mapper.writeValueAsString(meal);

		mockMvc.perform(
				MockMvcRequestBuilders.post("/new-meal")
   					.content(json)
						.contentType("application/json")
		);

		Assert.assertTrue(meals.count() == 1);
	}

	@Test
	public void reserveServing() throws Exception{
		User user = new User();
		user.setUsername("Billy");
		users.save(user);

		Meal meal = new Meal();

		meal.setUser(user);
		meal.setName("pizza");
		meal.setAvailableTime(LocalDateTime.now());
		meal.setRecipe("Awesome Toppings");
		meal.setCategory("Italian");
		meal.setServingCount(5);

		Serving serving = new Serving(meal);
		serving.setEta(LocalDateTime.now());
		serving.setUserEater(user);
		String json = mapper.writeValueAsString(serving);

		mockMvc.perform(
				MockMvcRequestBuilders.post("/reserve-serving")
						.content(json)
						.contentType("application/json")
		);

	}


	@Test
    public void getRequestTest() throws Exception {
	    ResultActions results = mockMvc.perform(
	            MockMvcRequestBuilders.get("/all-meals")
        );

	    // how you can access what the controller sent back:
	    //results.andReturn().getResponse().getContentAsString()

        // once you have the json response, you can deserialize it into
        // an arraylist of meals. then you can compare the arraylist
        // you got back from the controller with the arraylist you expect to get back.

        // OR, you can specify the json you expect to get back and see if
        // the controller returned that same json

        ArrayList test = mapper.readValue(results.andReturn().getResponse().getContentAsString(), ArrayList.class);

	    return;
    }

}

