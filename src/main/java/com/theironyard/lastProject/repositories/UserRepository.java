package com.theironyard.lastProject.repositories;

import com.theironyard.lastProject.entities.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Keith on 5/31/17.
 */
public interface UserRepository extends CrudRepository<User, Integer> {
}
