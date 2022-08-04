package com.Springboot.blog.service;

import com.Springboot.blog.payload.PostDto;
import com.Springboot.blog.payload.PostResponse;

public interface PostService {
    PostDto createPost(PostDto postDTO);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto getPostById(long id);

    PostDto updatePost(PostDto postDTO, long id);

    void deletePostById(long id);
}
