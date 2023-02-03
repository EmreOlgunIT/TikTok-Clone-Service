package com.example.MyProject.post;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor //Responsible for dependency injecting postService
@RestController
@RequestMapping("api/v1/post")
public class PostController {

    private final PostService postService;

    @PostMapping
    public void registerNewPost(@RequestBody Post post) { //@RequestBody: takes the request body and maps it to the Post object
        postService.addNewPost(post);
    }

    @GetMapping
    public List<Post> getPosts(){
        return postService.getPosts();
    }

    @GetMapping(path = "/{id}")
    public Optional<Post> getPostsById(@PathVariable("id") Integer id){
        return postService.getPostById(id);
    }

    @GetMapping(path = "/byuserid/{userid}")
    public Optional<List<Post>> getPostsByUserId(@PathVariable("userid") Integer userid){
        return postService.getPostsByUserId(userid);
    }

    @PutMapping()
    public void updatePost(@RequestParam(name = "id") Integer id, @RequestParam(name = "title") String title, @RequestParam(name = "description") String description) {
        postService.updatePost(id, title, description);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable("id") Integer id) {
        postService.deletePost(id);
    }

}
