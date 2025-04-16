package com.venkat.linkedin.posts_service.controller;

import com.venkat.linkedin.posts_service.dto.PostCreateRequestDto;
import com.venkat.linkedin.posts_service.dto.PostDto;
import com.venkat.linkedin.posts_service.service.PostsService;
import jakarta.servlet.http.HttpServletRequest;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostsController {

    private final PostsService postsService;
    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostCreateRequestDto postDto, HttpServletRequest httpServletRequest){
        PostDto createdPost = postsService.createPost(postDto, 1L);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

}
