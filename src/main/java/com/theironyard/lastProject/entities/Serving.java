package com.theironyard.lastProject.entities;

import javax.persistence.*;

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
    String eta;


    public Serving(Meal meal) {
        this.meal = meal;
    }

    public Serving(Meal meal, User userEater, String eta) {
        this.meal = meal;
        this.user = userEater;
        this.eta = eta;
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

    public String getEta() {
        return eta;
    }

    public void setEta(String eta) {
        this.eta = eta;
    }
}
