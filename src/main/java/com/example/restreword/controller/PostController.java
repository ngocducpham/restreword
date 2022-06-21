package com.example.restreword.controller;

import com.example.restreword.dto.PostInput;
import com.example.restreword.entity.Post;
import com.example.restreword.repo.PostRepository;
import com.example.restreword.exception.SettingNotFoundException;
import com.example.restreword.entity.User;
import com.example.restreword.exception.UserNotFoundException;
import com.example.restreword.repo.UserRepository;
import com.example.restreword.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/{userId}/post")
public class PostController {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostService postService;

    private User getUser(Integer id){
        Optional<User> userOptional = userRepository.findById(id);
        if(!userOptional.isPresent()){
            throw new UserNotFoundException("User not found: id-");
        }
        return  userOptional.get();
    }

    @GetMapping
    public ResponseEntity<Object> retrieveAllPosts(@PathVariable Integer userId){
        return ResponseEntity.ok(postService.findAll(userId));
    }

    @PostMapping()
    public ResponseEntity<Object> createPost(@PathVariable Integer userId, @Valid @RequestBody PostInput post){
        Integer postId = postService.create(userId,post);
        URI locationabc = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(postId)
                .toUri();

        return ResponseEntity.created(locationabc).build();
    }

    @PatchMapping("{id}")
    public ResponseEntity<Object> updatePost(@PathVariable Integer userId,@PathVariable Integer id, @RequestBody PostInput post){
        postService.update(id,post);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteSetting(@PathVariable Integer userId,@PathVariable Integer id){
        postService.delete(id);
        return ResponseEntity.ok().build();
    }
}
