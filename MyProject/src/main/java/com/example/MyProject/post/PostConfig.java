package com.example.MyProject.post;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

//Only used for populating posts table with dummy data for now.

@Configuration
public class PostConfig {

    @Bean
    CommandLineRunner commandLineRunner2(PostRepository postRepository) {
        return args -> {
            Post post1 = new Post("Kickflip", "Video of skate trick", "Skateboarder.mp4", 1);
            Post post2 = new Post("Snowboarder", "Snowboarding downhill", "Skiing.mp4", 1);
            Post post3 = new Post("Rallying", "dusty", "rally.mp4", 2);
            postRepository.saveAll(List.of(post1, post2, post3));
        };
    }

}
