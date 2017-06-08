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
    public void createUser(String username, String password, String phone, Integer token, String rating, Integer totalRating) {
        createUser(username, password, password, false, phone, token, rating, totalRating);
    }

    public void createUser(String username, String password, String passwordConfirmation, boolean isAdmin, String phone, Integer token, String rating, Integer totalRating) {
        User existingUser = users.findFirstByUsername(username);

        if ((existingUser == null) && password.equals(passwordConfirmation)) {
            token = 5;
            totalRating = 0;
            User u = new User(username, encoder.encode(password), phone, token, rating, totalRating);

            Authority authority = new Authority("ROLE_USER", u);
            users.save(u);
            authorities.save(authority);

            if (isAdmin) {
                authority = new Authority("ROLE_ADMIN", u);
                authorities.save(authority);
            }
        } else {
            throw new IllegalArgumentException("User already exists");
        }
    }

    public void rateUser(User user, User u, String newRating){
        u.setNewRating(user.getNewRating());

        users.save(user);
    }
}

//return  http serverlet respon`