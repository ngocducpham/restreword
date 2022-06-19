package com.example.restreword.rest;

import com.example.restreword.dto.UserDTO;
import com.example.restreword.entity.User;
import com.example.restreword.exception.UserNotFoundException;
import com.example.restreword.repo.UserRepository;
import com.example.restreword.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserResource {
    private final UserRepository userRepository;
    private final UserService userService;

    @GetMapping()
    public ResponseEntity<List<UserDTO>> retrieveAllUsers(){
        return ResponseEntity.ok( userService.findAll());
    }

    @GetMapping("{id}")
    public EntityModel<User> retrieveUser(@PathVariable int id){
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isEmpty()){
            throw new UserNotFoundException("User not found: id-");
        }

        EntityModel<User> resouce = EntityModel.of(userOptional.get());

        WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(this.getClass()).retrieveAllUsers());

        resouce.add(linkTo.withRel("all-users"));

        return resouce;
    }

    @PostMapping()
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user){
        User responseUser = user;
        User savedUser = userRepository.save(user);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable int id){
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isEmpty()){
            throw new UserNotFoundException("User not found: id-");
        }

        User user = userOptional.get();

        userRepository.deleteById(id);

        return ResponseEntity.ok().build();
    }
}
