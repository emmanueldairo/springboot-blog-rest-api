package com.Springboot.blog.controller;

import com.Springboot.blog.payload.PostDto;
import com.Springboot.blog.payload.PostResponse;
import com.Springboot.blog.service.PostService;
import com.Springboot.blog.utils.AppConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
@Api(value = "CRUD Rest APis for post resources")
@RestController
@RequestMapping()
public class PostController {
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    //create blog rest api
    @ApiOperation(value = "Create Post REST API")
    @PostMapping("/api/v1/posts")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDTO){
        return new ResponseEntity<>(postService.createPost(postDTO), HttpStatus.OK);
    }


    //get all posts rest api
    @ApiOperation(value = "Get all posts REST API")
    @GetMapping("/api/v1/posts")
    public PostResponse getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEAFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEAFAULT_PAGE_SIZE,required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEAFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEAFAULT_SORT_DIRECTION, required = false) String sortDir
    ){
        return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
    }

    //get post by id
    @ApiOperation(value = "Get post by ID REST API")
    @GetMapping("/api/v1/posts/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") long id){
        return  ResponseEntity.ok(postService.getPostById(id));
    }

    //update post by id rest api
    @ApiOperation(value = "Update Post by Id REST API")
    @PutMapping("/api/v1/posts/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDTO, @PathVariable(name = "id") long id){
        PostDto postResponse = postService.updatePost(postDTO,id);
        return new ResponseEntity<>(postResponse,HttpStatus.OK);
    }

    //delete post rest api
    @ApiOperation(value = "Delete Post REST API")
    @DeleteMapping("/api/v1/posts/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") long id){
        postService.deletePostById(id);

        return new ResponseEntity<>("Post entity deleted successfully", HttpStatus.OK);
    }
}
// 3am, GF, 7am