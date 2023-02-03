package com.example.MyProject.post;

import com.example.MyProject.person.Person;
import com.example.MyProject.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.MyProject.user.User;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Optional;

//This class is responsible for handling the business logic for posts.

@Service
@RequiredArgsConstructor //Replaces @Autowired for postRepository and userRepository
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public void addNewPost(Post post) {

        //Validate that user exists
        Optional<User> user = userRepository.findById(post.getUserId());
        if (!user.isPresent()) {
            throw new IllegalStateException("User with id " + post.getUserId() + " does not exist");
        }

        postRepository.save(post);
    }

    public List<Post> getPosts() {
        return postRepository.findAll();
    }

    public Optional<Post> getPostById(Integer id) {
        return postRepository.findById(id);
    }

    public Optional<List<Post>>getPostsByUserId(Integer userId) {
        return postRepository.findByUserId(userId);
    }

    public Post updatePost(Integer id, String title, String description) {
        Optional<Post> existingPost = postRepository.findById(id);

        if (!existingPost.isPresent()) {
            throw new IllegalStateException("Post with id " + id + " does not exist");
        }

        Post post = existingPost.get();

        if (!title.isEmpty()) {
            post.setTitle(title);
        }

        if (!description.isEmpty()) {
            post.setDescription(description);
        }

        return postRepository.save(post);
    }

    public void deletePost(Integer id) {
        postRepository.deleteById(id);
    }

}
