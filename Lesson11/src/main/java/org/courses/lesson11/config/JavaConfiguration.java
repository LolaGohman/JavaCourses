package org.courses.lesson11.config;

import org.courses.lesson11.repository.PasswordHasher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"org.courses.lesson11"})
public class JavaConfiguration {

    @Bean
    public PasswordHasher getPasswordHasher() {
        return new PasswordHasher();
    }

}
