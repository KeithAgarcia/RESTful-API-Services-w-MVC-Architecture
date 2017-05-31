package com.theironyard.lastProject.entities;

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
}
