package com.theironyard.lastProject.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by Keith on 5/31/17.
 */
@Entity
@Table(name = "servings")
public class Serving {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    Meal meal;

    @OneToOne
    User user;

    @Column
    LocalDateTime eta;

    @Column(nullable = false)
    boolean complete;

    String servingAmt;

    public Serving() {
    }

    public Serving(Meal meal) {
        this.meal = meal;
    }

    public Serving(LocalDateTime eta, String servingAmt){
        this.servingAmt = servingAmt;
        this.eta = eta;
    }

    public Serving(Meal meal, User userEater, LocalDateTime eta) {
        this.meal = meal;
        this.user = userEater;
        this.eta = eta;
        this.complete = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    public User getUserEater() {
        return user;
    }

    public void setUserEater(User userEater) {
        this.user = userEater;
    }

    public LocalDateTime getEta() {
        return eta;
    }

    public void setEta(LocalDateTime eta) {
        this.eta = eta;
    }

    public Boolean getComplete() {
        return complete;
    }

    public void setComplete(Boolean complete) {
        this.complete = complete;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public String getServingAmt() {
        return servingAmt;
    }

    public void setServingAmt(String servingAmt) {
        this.servingAmt = servingAmt;
    }
}
