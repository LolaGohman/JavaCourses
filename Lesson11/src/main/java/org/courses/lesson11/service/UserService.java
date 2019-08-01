package org.courses.lesson11.service;

import org.courses.lesson11.dto.User;
import org.courses.lesson11.exception.NoRightsToChangeDatabaseException;
import org.courses.lesson11.exception.TryToChangeDefaultUserException;
import org.courses.lesson11.exception.UnableToSaveUserException;

import java.util.List;
import java.util.Optional;

public interface UserService {

    void create(User user) throws UnableToSaveUserException;

    void update(User user) throws UnableToSaveUserException, TryToChangeDefaultUserException;

    void delete(User user) throws TryToChangeDefaultUserException;

    Optional<User> find(String username);

    Optional<User> find(long id);

    boolean checkIfUserCanBeLoggedIn(User user);

    void checkIfCurrentUserCanChangeDatabase(long id) throws NoRightsToChangeDatabaseException;

    List<User> getAllUsers();

}
