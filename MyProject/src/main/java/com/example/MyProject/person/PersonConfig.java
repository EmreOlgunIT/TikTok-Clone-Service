package com.example.MyProject.person;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class PersonConfig {

    @Bean
    CommandLineRunner commandLineRunner(PersonRepository personRepository) {
        return args -> {
            Person emre = new Person(1L, "Emre", "emremail.no", LocalDate.of(1997, Month.APRIL, 18));
            Person chet = new Person("Chet", "coolmail.com", LocalDate.of(1940, Month.APRIL, 20));
            personRepository.saveAll(List.of(emre, chet));
        };
    }
}
