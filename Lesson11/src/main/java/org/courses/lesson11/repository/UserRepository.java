package org.courses.lesson11.repository;

import org.courses.lesson11.dto.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User create(User user);

    User update(User user);

    boolean delete(User user);

    Optional<User> find(String username);

    Optional<User> find(long id);

    List<User> getAllUsers();

}
