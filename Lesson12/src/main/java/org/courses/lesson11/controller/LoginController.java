package org.courses.lesson11.controller;

import org.courses.lesson11.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String returnLogInPage() {
        return "login";
    }

    @GetMapping("/")
    public String returnHomePage(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        for (GrantedAuthority authority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
            if (authority.getAuthority().equals("ADMIN")) {
                return "users";
            }
        }
        return "simple.user.home.page";
    }


}
