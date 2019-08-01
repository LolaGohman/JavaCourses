package org.courses.lesson11.config.security;

import org.courses.lesson11.dto.User;
import org.courses.lesson11.service.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class VerifyAccessInterceptor implements HandlerInterceptor {

    private final UserService userService;

    public VerifyAccessInterceptor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Authentication newAuth = null;
        final Optional<User> userFromDatabase = userService.find(auth.getName());
        if (userFromDatabase.isPresent()) {
            final List<SimpleGrantedAuthority> authorityList = Collections
                    .singletonList(new SimpleGrantedAuthority(userFromDatabase.get().getIsAdmin()));
            if (auth.getPrincipal() != null) {
                newAuth = new UsernamePasswordAuthenticationToken(new org.springframework.security.core.userdetails.User(
                        userFromDatabase.get().getUsername(), userFromDatabase.get().getPassword(),authorityList
                        ), null, authorityList);
            }
        } else {
            request.getSession().invalidate();
        }
        SecurityContextHolder.getContext().setAuthentication(newAuth);
        return true;
    }

}
