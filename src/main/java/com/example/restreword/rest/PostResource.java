package com.example.restreword.rest;

import com.example.restreword.entity.Post;
import com.example.restreword.repo.PostRepository;
import com.example.restreword.exception.SettingNotFoundException;
import com.example.restreword.entity.User;
import com.example.restreword.exception.UserNotFoundException;
import com.example.restreword.repo.UserRepository;
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
public class PostResource {
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    private User getUser(Integer id){
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isEmpty()){
            throw new UserNotFoundException("User not found: id-");
        }
        return  userOptional.get();
    }

    @GetMapping
    public List<Post> retrieveAllPosts(@PathVariable Integer userId){
        User user = getUser(userId);

        return user.getPosts();
    }

    @PostMapping()
    public ResponseEntity<Object> createPost(@PathVariable Integer userId, @Valid @RequestBody Post post){
        User user = getUser(userId);

        post.setUser(user);

        postRepository.saveAndFlush(post);
        URI locationabc = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(post.getId())
                .toUri();

        return ResponseEntity.created(locationabc).build();
    }

    @PatchMapping()
    public ResponseEntity<Object> updatePost(@PathVariable Integer userId, @RequestBody Post setting){
        User user = getUser(userId);

        for (Post s : user.getPosts())  {
            if(s.getId().equals(setting.getId())) {
                s.setDescription(setting.getDescription());
                postRepository.saveAndFlush(s);
                return ResponseEntity.ok().build();
            }
        }

        throw  new SettingNotFoundException("Post Id: " + setting.getId());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteSetting(@PathVariable Integer userId,@PathVariable Integer id){
        User user = getUser(userId);

        for (Post s : user.getPosts())  {
            if(Objects.equals(s.getId(), id)) {
                postRepository.delete(s);
                return ResponseEntity.ok().build();
            }
        }

        throw new SettingNotFoundException("Post Id: " + id.toString());
    }
}
