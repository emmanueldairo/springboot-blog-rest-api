package com.Springboot.blog.controller;

import com.Springboot.blog.payload.PostDTO;
import com.Springboot.blog.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    //create blog rest api
    @PostMapping
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO){
        return new ResponseEntity<PostDTO>(postService.createPost(postDTO), HttpStatus.OK);
    }


    //get all posts rest api
    @GetMapping
    public List<PostDTO> getAllPosts(){
        return postService.getAllPosts();
    }

    //get post by id
    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable("id") long id){
        return  ResponseEntity.ok(postService.getPostById(id));
    }
}
