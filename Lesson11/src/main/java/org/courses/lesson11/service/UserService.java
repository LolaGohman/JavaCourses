package org.courses.lesson11.service;

import org.courses.lesson11.dto.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User create(User user);

    User update(User user);

    boolean delete(User user);

    Optional<User> find(String username);

    Optional<User> find(long id);

    boolean checkIfUserCanBeLoggedIn(User user);

    List<User> getAllUsers();

}
