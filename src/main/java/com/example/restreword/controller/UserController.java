package com.example.restreword.controller;

import com.example.restreword.common.ResponseTemplate;
import com.example.restreword.dto.PostOutput;
import com.example.restreword.dto.UserInput;
import com.example.restreword.dto.UserOutput;
import com.example.restreword.repo.UserRepository;
import com.example.restreword.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;

    @GetMapping()
    public ResponseEntity<Object> retrieveAllUsers() {
        return ResponseEntity.ok(ResponseTemplate
                .builder()
                .result(true)
                .message("OK")
                .data(userService.findAll())
                .build());
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> retrieveUser(@PathVariable int id) {
        return ResponseEntity.ok(ResponseTemplate
                .builder()
                .result(true)
                .message("OK")
                .data(userService.findOne(id))
                .build());
    }

    @PostMapping()
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserInput user) {
        Integer userId = userService.create(user);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(userId).toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.set("location", uri.toString());

        return ResponseEntity.ok()
                .headers(headers)
                .body((ResponseTemplate
                        .builder()
                        .result(true)
                        .message("OK")
                        .build()));
    }

    @PatchMapping("{id}")
    public ResponseEntity<Object> updateUser(@PathVariable Integer id, @Valid @RequestBody UserInput userInput) {
        userService.update(id, userInput);
        return ResponseEntity.ok(ResponseTemplate
                .builder()
                .result(true)
                .message("OK")
                .build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable int id) {
        userService.delete(id);
        return ResponseEntity.ok(ResponseTemplate
                .builder()
                .result(true)
                .message("OK")
                .build());
    }
}
