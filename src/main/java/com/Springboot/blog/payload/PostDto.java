package com.Springboot.blog.payload;

import com.Springboot.blog.entity.Comment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;
import java.util.Set;
@ApiModel(description = "Post model information")
@Data
public class PostDto {
    @ApiModelProperty(value = "Blog post id")
    private long ID;
    @ApiModelProperty(value = "Blog post title")
    @NotEmpty
    @Size(min = 2, message = "Post title should have at least 2 characters")
    private String title;
    @ApiModelProperty(value = "Blog post description")
    @NotEmpty
    @Size(min = 10, message = "Post description should have at least 10 characters")
    private String description;
    @ApiModelProperty(value = "Blog post content")
    @NotEmpty
    private String content;
    @ApiModelProperty(value = "Blog post comments")
    private Set<CommentDto> comments;
}
