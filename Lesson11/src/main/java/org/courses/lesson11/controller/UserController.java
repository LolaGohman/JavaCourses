package org.courses.lesson11.controller;

import org.courses.lesson11.dto.User;
import org.courses.lesson11.exception.NoRightsToChangeDatabaseException;
import org.courses.lesson11.exception.TryToChangeDefaultUserException;
import org.courses.lesson11.exception.UnableToSaveUserException;
import org.courses.lesson11.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/add")
    public String addUserGetMapping(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "add";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute("user") User user, HttpSession session)
            throws NoRightsToChangeDatabaseException, UnableToSaveUserException {
        long currentUserId = (Long) session.getAttribute("id");
        userService.checkIfCurrentUserCanChangeDatabase(currentUserId);
        userService.create(user);
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String personList(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }

    @GetMapping("/update/{id}")
    public String updateUserGetMapping(@PathVariable("id") long id, Model model) {
        if (userService.find(id).isPresent()) {
            User userToUpdate = userService.find(id).get();
            model.addAttribute("user", userToUpdate);
            return "edit";
        } else {
            throw new IllegalArgumentException("Invalid id: " + id);
        }
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id,
                             @ModelAttribute("user") User user, HttpSession session)
            throws NoRightsToChangeDatabaseException, TryToChangeDefaultUserException, UnableToSaveUserException {
        long currentUserId = (Long) session.getAttribute("id");
        userService.checkIfCurrentUserCanChangeDatabase(currentUserId);
        user.setId(id);
        userService.update(user);
        return "redirect:/home";
    }

    @GetMapping("/delete/{username}")
    public String deleteUser(@PathVariable("username") String username, HttpSession session)
            throws NoRightsToChangeDatabaseException, TryToChangeDefaultUserException {
        long currentUserId = (Long) session.getAttribute("id");
        userService.checkIfCurrentUserCanChangeDatabase(currentUserId);
        if (userService.find(username).isPresent()) {
            userService.delete(userService.find(username).get());
        }
        return "redirect:/home";
    }

}
