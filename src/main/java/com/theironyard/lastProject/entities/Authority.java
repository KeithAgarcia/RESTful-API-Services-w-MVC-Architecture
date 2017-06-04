package com.theironyard.lastProject.entities;

import javax.persistence.*;

/**
 * Created by Keith on 6/3/17.
 */
@Entity
@Table(name = "authorities")
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(nullable = false)
    String roleName;

    @ManyToOne
    User user;

    public Authority() {
    }

    public Authority(String roleName, User user) {
        this.roleName = roleName;
        this.user = user;
    }
}
