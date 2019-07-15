package org.courses.lesson11.controller;

import org.courses.lesson11.dto.User;
import org.courses.lesson11.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String returnLogInPage(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "login";
    }

    @PostMapping("/login")
    public String logIn(@ModelAttribute("user") User user,
                        HttpSession session,
                        Model model) {
        if (userService.checkIfUserCanBeLoggedIn(user)
                && userService.find(user.getUsername()).isPresent()) {
            User userToLogIn = userService.find(user.getUsername()).get();
            session.setAttribute("id", userToLogIn.getId());
            model.addAttribute("users", userService.getAllUsers());
            if(userToLogIn.getIsAdmin()){
                return "users";
            }else {
                return "simple.user.home.page";
            }
        } else {
            return "login";
        }
    }

}
