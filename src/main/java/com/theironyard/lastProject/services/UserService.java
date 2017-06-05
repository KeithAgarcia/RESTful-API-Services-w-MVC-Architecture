package com.theironyard.lastProject.services;

import com.theironyard.lastProject.entities.Authority;
import com.theironyard.lastProject.entities.User;
import com.theironyard.lastProject.repositories.AuthorityRepository;
import com.theironyard.lastProject.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Created by Keith on 6/3/17.
 */
@Service
public class UserService {
    UserRepository users;
    AuthorityRepository authorities;
    PasswordEncoder encoder;


    public UserService(UserRepository users, AuthorityRepository authorities, PasswordEncoder encoder) {
        this.users = users;
        this.authorities = authorities;
        this.encoder = encoder;
    }
    public void createUser(String username, String password, String phone, int token) {
        createUser(username, password, password, false, phone, token);
    }

    public void createUser(String username, String password, String passwordConfirmation, boolean isAdmin, String phone, int token) {
        User existingUser = users.findFirstByUsername(username);

        if ((existingUser == null) && password.equals(passwordConfirmation)) {
            token = 5;
            User u = new User(username, encoder.encode(password), phone, token);

            Authority authority = new Authority("ROLE_USER", u);
            users.save(u);
            authorities.save(authority);

            if (isAdmin) {
                authority = new Authority("ROLE_ADMIN", u);
                authorities.save(authority);
            }
        }
    }}
