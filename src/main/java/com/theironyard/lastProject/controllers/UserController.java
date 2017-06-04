package com.theironyard.lastProject.controllers;

import com.theironyard.lastProject.entities.User;
import com.theironyard.lastProject.repositories.UserRepository;
import com.theironyard.lastProject.services.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * Created by Keith on 6/3/17.
 */
@RestController
public class UserController {
    UserService userService;
    UserRepository users;

    public UserController(UserService userService, UserRepository users) {
        this.users = users;
        this.userService = userService;
    }

    @PostConstruct
    public void init() {
        if (users.count() == 0) {
            userService.createUser("Steve", "test", 0);
        }
    }

    @RequestMapping(path = "/new-user", method = RequestMethod.POST)
    public void newUser(User user, String passwordConfirm, int token, boolean isAdmin) {
        userService.createUser(user.getUsername(), user.getPassword(), passwordConfirm, isAdmin, token);
    }
}