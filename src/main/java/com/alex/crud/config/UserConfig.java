package com.alex.crud.config;

import com.alex.crud.model.User;
import com.alex.crud.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class UserConfig {

    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository) {
        return args -> {
            User user1 = new User("Alex", "alex@gmail.com", 24);
            User user2 = new User("Tolya", "tolya@professional.com", 24);
            User user3 = new User("Someone", "someone@example.com", 65);
            User user4 = new User("Oleg", "oleg@gmail.com", 45);
            User user5 = new User("Vasya", "Pupkin@mail.ru", 32);
            userRepository.saveAll(List.of(user1, user2, user3, user4, user5));
        };
    }
}
