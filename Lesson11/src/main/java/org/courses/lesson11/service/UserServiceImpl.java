package org.courses.lesson11.service;

import org.courses.lesson11.dto.User;
import org.courses.lesson11.repository.PasswordHasher;
import org.courses.lesson11.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;

    public UserServiceImpl(UserRepository userRepository, PasswordHasher passwordHasher) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
    }


    @Override
    public User create(User user) {
        if (checkIfUserFieldsAreNotEmpty(user) && userRepository.find(user.getUsername()).isEmpty()) {
            return userRepository.create(user);
        }
        throw new IllegalArgumentException("Can not be created! Maybe, you've entered empty fields " +
                "or username already exists!");
    }

    @Override
    public User update(User user) {
        user.setPassword(passwordHasher.encrypt(user.getPassword()));
        if (checkIfUserCanBeUpdated(user)) {
            if (!user.getUsername().equals("admin")) {
                return userRepository.update(user);
            } else {
                throw new IllegalArgumentException("Sorry, you can not update default user!");
            }
        } else {
            throw new IllegalArgumentException("Can not be updated! Maybe, you've entered empty fields " +
                    "or username already exists!");
        }
    }

    @Override
    public boolean delete(User user) {
        if (user.getUsername().equals("admin")) {
            throw new IllegalArgumentException("Sorry, default user can not be deleted!");
        }
        return userRepository.delete(user);
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

    private boolean checkIfUserCanBeUpdated(User user) {
        if (checkIfUserFieldsAreNotEmpty(user)) {
            if (userRepository.find(user.getUsername()).isEmpty()) {
                return true;
            } else {
                return userRepository.find(user.getUsername()).get().getId()
                        == user.getId();
            }
        } else {
            return false;
        }
    }

    private boolean checkIfUserFieldsAreNotEmpty(User user) {
        return user.getUsername() != null && user.getPassword() != null &&
                !user.getUsername().isBlank() && !user.getPassword().isBlank();
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
}
