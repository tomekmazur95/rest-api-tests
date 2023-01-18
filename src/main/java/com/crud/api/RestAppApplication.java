package com.crud.api;

import com.crud.api.dto.CreateUser;
import com.crud.api.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RestAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestAppApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(UserService userService) {
        return args -> {
            userService.create(new CreateUser("Tomasz", "Mazur"));
            userService.create(new CreateUser("Krzys", "Mazur"));
            userService.create(new CreateUser("Gosia", "Mazur"));

        };
    }
}
