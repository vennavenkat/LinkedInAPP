package com.venkat.linkedin.posts_service.controller;

import com.venkat.linkedin.posts_service.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikesController {
    private final PostLikeService postLikeService;

    @PostMapping("/{postId}")
    public ResponseEntity<Void> likePost(@PathVariable Long postId){
        postLikeService.likePost(postId, 1L);
        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> unlikePost(@PathVariable Long postId){
        postLikeService.unllikePost(postId, 1L);
        return ResponseEntity.noContent().build();

    }


}
