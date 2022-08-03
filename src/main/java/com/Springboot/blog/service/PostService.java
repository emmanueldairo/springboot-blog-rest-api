package com.Springboot.blog.service;

import com.Springboot.blog.payload.PostDTO;

public interface PostService {
    PostDTO createPost(PostDTO postDTO);
}
