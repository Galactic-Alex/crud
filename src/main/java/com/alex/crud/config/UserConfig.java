package com.alex.crud.config;

import com.alex.crud.model.User;
import com.alex.crud.model.UserRole;
import com.alex.crud.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Set;

@Configuration
public class UserConfig {

    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository) {
        return args -> {
            User user1 = new User("Alex", "alex@gmail.com", 24, "123");
            user1.setRoles(Set.of(new UserRole("ROLE_USER"), new UserRole("ROLE_ADMIN")));
            User user2 = new User("Tolya", "tolya@professional.com", 24, "123");
            user2.setRoles(Set.of(new UserRole("ROLE_USER")));
            User user3 = new User("Someone", "someone@example.com", 65, "123");
            user3.setRoles(Set.of(new UserRole("ROLE_USER")));
            User user4 = new User("Oleg", "oleg@gmail.com", 45, "123");
            user4.setRoles(Set.of(new UserRole("ROLE_USER")));
            User user5 = new User("Vasya", "Pupkin@mail.ru", 32, "123");
            user5.setRoles(Set.of(new UserRole("ROLE_USER")));
            userRepository.saveAll(List.of(user1, user2, user3, user4, user5));
        };
    }
}
