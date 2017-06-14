package com.theironyard.lastProject.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

/**
 * Created by Keith on 5/31/17.
 */
@Entity
@Table(name= "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(nullable = false, unique = true)
    String username;

    @Column
    String phone;

    @Column(nullable = false)
    String password;

    @Column(nullable = false)
    boolean enabled;

    @Column
    Integer token;

    @Column(nullable = false)
    Double rating;

    @Column(nullable = false)
    int totalRatings;

    @Column(nullable = false)
    int totalCookMeals;


    String passwordConfirm;

    public User() {
    }

    public void addRating(int newRating) {
        Double ratingSum = getTotalRatings() * getRating();
        ratingSum += newRating;
        setTotalRatings(getTotalRatings() + 1);
        setRating(ratingSum / getTotalRatings());
    }

    public User(String username, String password, Integer token){
        this.username = username;
        this.password = password;
        this.enabled = true;

    }

    public User(String username, String password, String phone, Integer token, Double rating, Integer totalRatings, int totalCookMeals) {
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.token = token;
        this.rating = rating;
        this.totalRatings = totalRatings;
        this.totalCookMeals = totalCookMeals;
        this.enabled = true;
   }

    public User(String username, String phone, String password, boolean enabled, Integer token, Double rating, Integer totalRatings, String passwordConfirm) {
        this.username = username;
        this.phone = phone;
        this.password = password;
        this.enabled = enabled;
        this.token = token;
        this.rating = rating;
        this.totalRatings = totalRatings;
        this.passwordConfirm = passwordConfirm;
    }

    public String getUsername() {
        return username;
    }

    public User(Double rating) {
        this.rating = rating;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getToken() {
        return token;
    }

    public void setToken(Integer token) {
        this.token = token;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public Double getRating() {
        return rating;
    }

    public Integer getTotalRatings() {
        return totalRatings;
    }

    public void setTotalRatings(Integer totalRatings) {
        this.totalRatings = totalRatings;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public void setTotalRatings(int totalRatings) {
        this.totalRatings = totalRatings;
    }

    public int getTotalCookMeals() {
        return totalCookMeals;
    }

    public void setTotalCookMeals(int totalCookMeals) {
        this.totalCookMeals = totalCookMeals;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return id == user.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
