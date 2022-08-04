package com.Springboot.blog.service.impl;

import com.Springboot.blog.entity.Comment;
import com.Springboot.blog.entity.Post;
import com.Springboot.blog.exception.ResourceNotFoundException;
import com.Springboot.blog.payload.CommentDto;
import com.Springboot.blog.repository.CommentRepository;
import com.Springboot.blog.repository.PostRepository;
import com.Springboot.blog.service.CommentService;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment comment = mapToEntity(commentDto);
        //retrieve the post entity by id
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","id",postId));

        //set post to comment entity
        comment.setPost(post);
        //comment entity to DB
        Comment newComment = commentRepository.save(comment);

        return mapToDto(newComment);
    }

    private CommentDto mapToDto(Comment comment){
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setName(commentDto.getName());
        commentDto.setEmail(commentDto.getEmail());
        commentDto.setBody(commentDto.getBody());

        return commentDto;
    }

    private Comment mapToEntity(CommentDto commentDto){
        Comment comment = new Comment();
        comment.setId(commentDto.getId());
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        return comment;
    }
}
