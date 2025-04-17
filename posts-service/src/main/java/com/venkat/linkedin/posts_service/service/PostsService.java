package com.venkat.linkedin.posts_service.service;

import com.venkat.linkedin.posts_service.dto.PostCreateRequestDto;
import com.venkat.linkedin.posts_service.dto.PostDto;
import com.venkat.linkedin.posts_service.entity.Post;
import com.venkat.linkedin.posts_service.exception.ResourceNotFoundException;
import com.venkat.linkedin.posts_service.repository.PostsRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostsService {
    private final PostsRepository postsRepository;
    private final ModelMapper modelMapper;
    public PostDto createPost(PostCreateRequestDto postDto, Long userId) {
        Post post = modelMapper.map(postDto, Post.class);
        post.setUserId(userId);

        Post savedPost = postsRepository.save(post);
        return modelMapper.map(savedPost, PostDto.class);
    }

    public PostDto getPostById(Long postId) {
        log.debug("Retriving post with ID: {}", postId);
        Post post =  postsRepository.findById(postId).orElseThrow(() ->
               new ResourceNotFoundException("Post not found with id: "+postId) );
        return modelMapper.map(post, PostDto.class);
    }

    public List<PostDto> getAllPostsOfUser(Long userId) {
        List<Post> posts = postsRepository.findByUserId(userId);
        return posts
                .stream()
                .map((element) -> modelMapper.map(element, PostDto.class))
                .collect(Collectors.toList());
    }
}
