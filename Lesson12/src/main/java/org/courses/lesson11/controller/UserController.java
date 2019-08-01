package org.courses.lesson11.controller;

import org.courses.lesson11.dto.User;
import org.courses.lesson11.exception.TryToChangeDefaultUserException;
import org.courses.lesson11.exception.UnableToSaveUserException;
import org.courses.lesson11.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/add")
    public String addUserGetMapping(Model model) {
        model.addAttribute("user", new User());
        return "add";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute("user") User user, HttpServletRequest request)
            throws UnableToSaveUserException {
        if (request.getParameter("admin") != null && request.getParameter("admin").equals("Admin")) {
            user.setIsAdmin("ADMIN");
        } else {
            user.setIsAdmin("USER");
        }
        userService.create(user);
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String personList(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        for (GrantedAuthority authority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
            if (authority.getAuthority().equals("ADMIN")) {
                return "users";
            }
        }
        return "simple.user.home.page";
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
                             HttpServletRequest request,
                             @ModelAttribute("user") User user)
            throws TryToChangeDefaultUserException, UnableToSaveUserException {
        user.setId(id);
        if (request.getParameter("admin") != null && request.getParameter("admin").equals("Admin")) {
            user.setIsAdmin("ADMIN");
        } else {
            user.setIsAdmin("USER");
        }
        userService.update(user);
        return "redirect:/home";
    }

    @GetMapping("/delete/{username}")
    public String deleteUser(@PathVariable("username") String username)
            throws TryToChangeDefaultUserException {
        if (userService.find(username).isPresent()) {
            userService.delete(userService.find(username).get());
        }
        return "redirect:/home";
    }

}
