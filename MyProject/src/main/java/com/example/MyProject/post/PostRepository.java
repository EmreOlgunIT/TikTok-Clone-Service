package com.example.MyProject.post;

import com.example.MyProject.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//This class is responsible for accessing the "Post" table in database.

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    Optional<List<Post>> findByUserId(Integer id);  //findBy + any field name

}
