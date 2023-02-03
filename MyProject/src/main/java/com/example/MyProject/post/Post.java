package com.example.MyProject.post;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//Post holds filename of video, title, description, and user who uploaded video

@AllArgsConstructor //Generates a constructor with all arguments
@NoArgsConstructor //Generates a constructor with no arguments
@Data //Generates getters and setters
@Entity
@Table
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String title;
    private String description;
    private String fileName;
    private Integer userId;

    //Used for creating new posts
    public Post(String title, String description, String fileName, Integer userId) {
        this.title = title;
        this.description = description;
        this.fileName = fileName;
        this.userId = userId;
    }

}
