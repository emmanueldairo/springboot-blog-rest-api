package com.Springboot.blog.service.impl;

import com.Springboot.blog.entity.Post;
import com.Springboot.blog.exception.ResourceNotFoundException;
import com.Springboot.blog.payload.PostDTO;
import com.Springboot.blog.payload.PostResponse;
import com.Springboot.blog.repository.PostRepository;
import com.Springboot.blog.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDTO createPost(PostDTO postDTO) {
        Post post = mapToEntity(postDTO);

        Post newPost = postRepository.save(post);

        PostDTO postResponse = mapToDTO(newPost);

        return postResponse;
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo,pageSize, Sort.by(sortBy));

        Page<Post> posts = postRepository.findAll(pageable);
        //get content for page object
        List<Post> listOfPosts = posts.getContent();

        List<PostDTO> content = listOfPosts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();

        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }

    @Override
    public PostDTO getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post", "id", id));
        return mapToDTO(post);
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, long id) {
        //get a post by id from DB
        Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post", "id", id));
        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setContent(postDTO.getContent());

        Post updatedPost = postRepository.save(post);

        return mapToDTO(updatedPost);
    }

    @Override
    public void deletePostById(long id) {
        //get a post by id from DB
        Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(post);

    }

    //convert entity into DTO
    private PostDTO mapToDTO(Post post)
    {
        PostDTO postDTO = new PostDTO();
        postDTO.setID(post.getId());
        postDTO.setTitle(post.getTitle());
        postDTO.setDescription(post.getDescription());
        postDTO.setContent(post.getContent());

        return postDTO;
    }

    //convert DTO to entity
    private Post mapToEntity(PostDTO postDTO){
        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setDescription(postDTO.getDescription());

        return post;
    }
}
