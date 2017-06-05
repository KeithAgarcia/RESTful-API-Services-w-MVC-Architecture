package com.theironyard.lastProject.controllers;

import com.theironyard.lastProject.entities.User;
import com.theironyard.lastProject.repositories.UserRepository;
import com.theironyard.lastProject.services.UserService;
import org.springframework.web.bind.annotation.*;

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
            userService.createUser("Keith", "admin","704-578-5872", 5);
        }
    }
    @CrossOrigin
    @RequestMapping(path = "/new-user", method = RequestMethod.POST)
    public void newUser(@RequestBody User user) {
        userService.createUser(user.getUsername(), user.getPassword(), user.getPassword(), false, user.getPhone(), user.getToken());
    }

    @RequestMapping(path = "/select-user/{id}", method = RequestMethod.POST)
    public User selectUser(@PathVariable("id") int id){
        return users.findOne(id);
    }
}
