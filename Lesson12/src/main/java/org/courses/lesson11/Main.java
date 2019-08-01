package org.courses.lesson11;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaRepositories("org.courses.lesson11.repository")
@EnableTransactionManagement
public class Main {

    public static void main(String[] args) {

        SpringApplication.run(Main.class, args);
    }





}
