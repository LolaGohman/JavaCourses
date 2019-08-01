package org.courses.lesson11.service;

import org.courses.lesson11.dto.User;
import org.courses.lesson11.exception.NoRightsToChangeDatabaseException;
import org.courses.lesson11.exception.TryToChangeDefaultUserException;
import org.courses.lesson11.exception.UnableToSaveUserException;
import org.courses.lesson11.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final String DEFAULT_USER_NAME = "admin";

    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;

    public UserServiceImpl(UserRepository userRepository, PasswordHasher passwordHasher) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
    }

    @Override
    public void create(User user) throws UnableToSaveUserException {
        if (checkIfUserFieldsAreNotEmpty(user) && userRepository.find(user.getUsername()).isEmpty()) {
            user.setPassword(passwordHasher.encrypt(user.getPassword()));
            userRepository.create(user);
        } else {
            throw new UnableToSaveUserException();
        }
    }

    @Override
    public void update(User user) throws UnableToSaveUserException, TryToChangeDefaultUserException {
        if (checkIfUserCanBeUpdated(user)) {
            if (userRepository.find(DEFAULT_USER_NAME).isPresent()
                    && userRepository.find(DEFAULT_USER_NAME).get().getId() != user.getId()) {

                user.setPassword(passwordHasher.encrypt(user.getPassword()));
                userRepository.update(user);
            } else {
                throw new TryToChangeDefaultUserException();
            }
        } else {
            throw new UnableToSaveUserException();
        }
    }

    @Override
    public void delete(User user) throws TryToChangeDefaultUserException {
        if (user.getUsername().equals(DEFAULT_USER_NAME)) {
            throw new TryToChangeDefaultUserException();
        }
        userRepository.delete(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public Optional<User> find(long id) {
        return userRepository.find(id);
    }

    @Override
    public Optional<User> find(String username) {
        return userRepository.find(username);
    }

    @Override
    public boolean checkIfUserCanBeLoggedIn(User user) {
        if (userRepository.find(user.getUsername()).isPresent()) {
            User userFromDatabase = userRepository.find(user.getUsername()).get();
            return userFromDatabase.getPassword()
                    .equals(passwordHasher.encrypt(user.getPassword()));
        } else {
            return false;
        }
    }

    @Override
    public void checkIfCurrentUserCanChangeDatabase(long id) throws NoRightsToChangeDatabaseException {
        if (find(id).isEmpty() || find(id).isPresent() && !find(id).get().getIsAdmin()) {
            throw new NoRightsToChangeDatabaseException();
        }
    }

    private boolean checkIfUserCanBeUpdated(User user) {
        if (checkIfUserFieldsAreNotEmpty(user)) {
            if (userRepository.find(user.getUsername()).isPresent()) {
                return userRepository.find(user.getUsername()).get().getId()
                        == user.getId();
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    private boolean checkIfUserFieldsAreNotEmpty(User user) {
        return user.getUsername() != null && user.getPassword() != null &&
                !user.getUsername().isBlank() && !user.getPassword().isBlank();
    }


}
