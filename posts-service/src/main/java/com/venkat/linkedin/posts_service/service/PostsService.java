package com.venkat.linkedin.posts_service.service;

import com.venkat.linkedin.posts_service.auth.UserContextHolder;
import com.venkat.linkedin.posts_service.clients.ConnectionsClient;
import com.venkat.linkedin.posts_service.dto.PersonDto;
import com.venkat.linkedin.posts_service.dto.PostCreateRequestDto;
import com.venkat.linkedin.posts_service.dto.PostDto;
import com.venkat.linkedin.posts_service.entity.Post;
import com.venkat.linkedin.posts_service.event.PostCreatedEvent;
import com.venkat.linkedin.posts_service.exception.ResourceNotFoundException;
import com.venkat.linkedin.posts_service.repository.PostsRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostsService {
    private final PostsRepository postsRepository;
    private final ModelMapper modelMapper;
    private final ConnectionsClient connectionsClient;

    private final KafkaTemplate<Long, PostCreatedEvent> kafkaTemplate;
    public PostDto createPost(PostCreateRequestDto postDto) {
        Long userId = UserContextHolder.getCurrentUserId();
        Post post = modelMapper.map(postDto, Post.class);
        post.setUserId(userId);

        Post savedPost = postsRepository.save(post);
        PostCreatedEvent postCreatedEvent = PostCreatedEvent.builder()
                .postId(savedPost.getId())
                .creatorId(userId)
                .content(savedPost.getContent())
                .build();

        kafkaTemplate.send("post-created-topic", postCreatedEvent);

        return modelMapper.map(savedPost, PostDto.class);
    }

    public PostDto getPostById(Long postId) {
        log.debug("Retriving post with ID: {}", postId);

        Long userId = UserContextHolder.getCurrentUserId();

        List<PersonDto> fistConnections = connectionsClient.getFirstConnections();

        //TODO send Notifications all connections

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
