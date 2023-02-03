package com.example.MyProject.user;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

//Only used for populating user table with dummy data for now.
@Configuration
@RequiredArgsConstructor
public class UserConfig {

    private final PasswordEncoder passwordEncoder;
    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository) {
        return args -> {
            User user = new User(1, "Emre", "Olgun", "emre.olgun@email.com", passwordEncoder.encode("123"), Role.USER);
            userRepository.save(user);
        };
    }
}