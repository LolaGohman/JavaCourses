package org.courses.lesson11.service;

import org.courses.lesson11.dto.User;
import org.courses.lesson11.exception.TryToChangeDefaultUserException;
import org.courses.lesson11.exception.UnableToSaveUserException;
import org.courses.lesson11.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final String DEFAULT_USER_NAME = "admin";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordHasher;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordHasher) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
    }

    @Override
    public void create(User user) throws UnableToSaveUserException {
        if (checkIfUserFieldsAreNotEmpty(user) && userRepository.findByUsername(user.getUsername()).isEmpty()) {
            user.setPassword(passwordHasher.encode(user.getPassword()));
            userRepository.save(user);
        } else {
            throw new UnableToSaveUserException();
        }
    }

    @Override
    public void update(User user) throws UnableToSaveUserException, TryToChangeDefaultUserException {
        if (checkIfUserCanBeUpdated(user)) {
            if (userRepository.findByUsername(DEFAULT_USER_NAME).isPresent()
                    && userRepository.findByUsername(DEFAULT_USER_NAME).get().getId() != user.getId()) {
                user.setPassword(passwordHasher.encode(user.getPassword()));
                userRepository.save(user);
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
        return userRepository.findAll();
    }

    @Override
    public Optional<User> find(long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> find(String username) {
        return userRepository.findByUsername(username);
    }


    private boolean checkIfUserCanBeUpdated(User user) {
        if (checkIfUserFieldsAreNotEmpty(user)) {
            if (userRepository.findByUsername(user.getUsername()).isPresent()) {
                return userRepository.findByUsername(user.getUsername()).get().getId()
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
