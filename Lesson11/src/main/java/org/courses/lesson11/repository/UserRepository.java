package org.courses.lesson11.repository;

import org.courses.lesson11.dto.User;
import java.util.List;
import java.util.Optional;

public interface UserRepository {

    void create(User user);

    void update(User user);

    void delete(User user);

    Optional<User> find(String username);

    Optional<User> find(long id);

    List<User> getAllUsers();

}
