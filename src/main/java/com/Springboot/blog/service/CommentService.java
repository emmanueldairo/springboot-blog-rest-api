package com.Springboot.blog.service;

import com.Springboot.blog.payload.CommentDto;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);
}
