package com.example.restreword.controller;

import com.example.restreword.dto.SettingInput;
import com.example.restreword.dto.SettingOutput;
import com.example.restreword.repo.SettingRepository;
import com.example.restreword.entity.User;
import com.example.restreword.exception.UserNotFoundException;
import com.example.restreword.repo.UserRepository;
import com.example.restreword.service.SettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/{userId}/setting")
public class SettingController {
    private final UserRepository userRepository;

    private final SettingRepository settingRepository;
    private final SettingService settingService;

    private User getUser(Integer id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            throw new UserNotFoundException("User not found: id-");
        }
        return userOptional.get();
    }

    @GetMapping
    public ResponseEntity<Object> retrieveAllSetting(@PathVariable Integer userId) {
        return new ResponseEntity<>(settingService.findAll(userId), HttpStatus.OK);

    }

    @PostMapping()
    public ResponseEntity<Object> createSetting(@PathVariable Integer userId, @Valid @RequestBody SettingInput settingInput) {
        return ResponseEntity.status(settingService.create(userId,settingInput)).build();
    }

    @PatchMapping()
    public ResponseEntity<Object> updateSetting(@PathVariable Integer userId, @RequestBody SettingInput setting) {
        settingService.update(userId,setting);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{key}")
    public ResponseEntity<Object> deleteSetting(@PathVariable Integer userId, @PathVariable String key) {
        settingService.delete(userId,key);
        return ResponseEntity.ok().build();
    }
}