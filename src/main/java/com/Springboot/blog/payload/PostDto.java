package com.Springboot.blog.payload;

import lombok.Data;

@Data
public class PostDto {
    private long ID;
    private String title;
    private String description;
    private String content;
}
