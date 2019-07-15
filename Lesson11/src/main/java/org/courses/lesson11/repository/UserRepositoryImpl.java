package org.courses.lesson11.repository;

import org.courses.lesson11.dto.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private List<User> list = new ArrayList<>(Arrays.asList(new User(1, "admin", "21232f297a57a5a743894a0e4a801fc3", true),
            new User(2, "user", "ee11cbb19052e40b07aac0ca060c23ee", false)));
    private final PasswordHasher passwordHasher;

    public UserRepositoryImpl(PasswordHasher passwordHasher) {
        this.passwordHasher = passwordHasher;
    }

    @Override
    public User create(User user) {
        user.setPassword(passwordHasher.encrypt(user.getPassword()));
        list.add(user);
        return user;
    }

    @Override
    public User update(User user) {
        delete(find(user.getId()).get());
        create(user);
        return user;
    }

    @Override
    public boolean delete(User user) {
        list.remove(user);
        return true;
    }

    @Override
    public Optional<User> find(String username) {
        for (User user : list) {
            if (user.getUsername().equals(username)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> find(long id) {
        for (User user : list) {
            if (user.getId() == id) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<User> getAllUsers() {
        return list;
    }


}
