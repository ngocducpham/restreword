package com.example.restreword.controller;

import com.example.restreword.annotation.IsAdmin;
import com.example.restreword.common.ResponseTemplate;
import com.example.restreword.dto.UserInput;
import com.example.restreword.repo.UserRepository;
import com.example.restreword.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;

    @GetMapping()
    @IsAdmin
    public ResponseEntity<Object> retrieveAllUsers() {
        return ResponseTemplate.success(userService.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> retrieveUser(@PathVariable int id) {
        return ResponseTemplate.success(userService.findOne(id));
    }

    @PostMapping()
    @IsAdmin
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserInput user) {
        Integer responseTemplate = userService.create(user);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseTemplate).toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.set("location", uri.toString());

        return ResponseTemplate.success(headers);
    }

    @PatchMapping("{id}")
    @IsAdmin
    public ResponseEntity<Object> updateUser(@PathVariable Integer id, @Valid @RequestBody UserInput userInput) {
        userService.update(id, userInput);
        return ResponseTemplate.success();
    }

    @DeleteMapping("{id}")
    @IsAdmin
    public ResponseEntity<Object> deleteUser(@PathVariable int id) {
        userService.delete(id);
        return ResponseTemplate.success();
    }
}
