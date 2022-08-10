package com.Springboot.blog.payload;

import com.Springboot.blog.entity.Comment;
import lombok.Data;

import java.util.Set;

@Data
public class PostDto {
    private long ID;
    private String title;
    private String description;
    private String content;
    private Set<CommentDto> comments;
}
