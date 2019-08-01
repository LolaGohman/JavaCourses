package org.courses.lesson11.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    private static final int THIRTY_ONE_DAYS_IN_SECONDS = 2678400;
    private static final String REMEMBER_ME_KEY = "remember-me";
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;


    private final VerifyAccessInterceptor verifyAccessInterceptor;


    public WebSecurityConfig(PasswordEncoder passwordEncoder
            ,
                             @Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService,
                             VerifyAccessInterceptor verifyAccessInterceptor) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.verifyAccessInterceptor = verifyAccessInterceptor;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/add", "/update/**", "/delete/**").hasAuthority("ADMIN")
                .antMatchers("/**").hasAnyAuthority("USER", "ADMIN")
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and().rememberMe()
                .tokenValiditySeconds(THIRTY_ONE_DAYS_IN_SECONDS)
                .key(REMEMBER_ME_KEY)
                .and()
                .logout()
                .permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(verifyAccessInterceptor).addPathPatterns("/**");
    }
}
