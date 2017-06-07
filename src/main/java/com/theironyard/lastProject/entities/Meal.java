package com.theironyard.lastProject.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Keith on 5/31/17.
 */
@Entity
@Table(name = "meals")
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @OneToMany
    List<Serving> servings;

    @ManyToOne
    User user;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    LocalDateTime availableTime;

    @Column(nullable = false)
    String recipe;

    @Column(nullable = false)
    String category;

    @Column(nullable = false)
    String street;

    @Column(nullable = false)
    String city;

    @Column(nullable = false)
    String state;

    @Column(nullable = false)
    String zipcode;

    @Column(nullable = false)
    String add_info;

    public int servingCount;

    public Meal() {
    }

    public Meal(String name, LocalDateTime availableTime, String recipe, String category, String street, String city, String state, String zipcode, String add_info, int servingCount) {
//        this.user = user;
        this.name = name;
        this.availableTime = availableTime;
        this.recipe = recipe;
        this.category = category;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
        this.add_info = add_info;
        this.servingCount = servingCount;
    }

    public Meal(List<Serving> servings, User user, String name, LocalDateTime availableTime, String recipe, String category, String street, String city, String state, String zipcode, String add_info, int servingCount) {
        this.servings = servings;
        this.user = user;
        this.name = name;
        this.availableTime = availableTime;
        this.recipe = recipe;
        this.category = category;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
        this.add_info = add_info;
        this.servingCount = servingCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Serving> getServings() {
        return servings;
    }

    public void setServings(List<Serving> servings) {
        this.servings = servings;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getAvailableTime() {
        return availableTime;
    }

    public void setAvailableTime(LocalDateTime availableTime) {
        this.availableTime = availableTime;
    }

    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getServingCount() {
//        return servings.size();
        return servingCount;
    }

    public void setServingCount(int servingCount) {
        this.servingCount = servingCount;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getAdd_info() {
        return add_info;
    }

    public void setAdd_info(String add_info) {
        this.add_info = add_info;
    }
}

