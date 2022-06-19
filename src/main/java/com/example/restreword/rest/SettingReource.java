package com.example.restreword.rest;

import com.example.restreword.entity.Setting;
import com.example.restreword.exception.SettingNotFoundException;
import com.example.restreword.repo.SettingRepository;
import com.example.restreword.entity.User;
import com.example.restreword.exception.UserNotFoundException;
import com.example.restreword.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.*;

@RestController
@RequestMapping("/api/user/{userId}/setting")
public class SettingReource {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SettingRepository settingRepository;

    private User getUser(Integer id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            throw new UserNotFoundException("User not found: id-");
        }
        return userOptional.get();
    }

    @GetMapping
    public List<Setting> retrieveAllSetting(@PathVariable Integer userId) {
        return getUser(userId).getSettings();
    }

    @PostMapping()
    public ResponseEntity<Object> createSetting(@PathVariable Integer userId, @Valid @RequestBody Setting setting) {
        User user = getUser(userId);

        for (Setting s : user.getSettings()) {
            if (s.getKey().equals(setting.getKey())) {
                return new ResponseEntity<>("Setting already exists", HttpStatus.CONFLICT);
            }
        }
        setting.setUsers(Arrays.asList(user));

        settingRepository.saveAndFlush(setting);
        URI locationabc = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(setting.getKey())
                .toUri();

        return ResponseEntity.created(locationabc).build();
    }

    @PatchMapping()
    public ResponseEntity<Object> updateSetting(@PathVariable Integer userId, @RequestBody Setting setting) {
        User user = getUser(userId);

        for (Setting s : user.getSettings()) {
            if (s.getKey().equals(setting.getKey())) {
                s.setValue(setting.getValue());
                settingRepository.saveAndFlush(s);
                return ResponseEntity.ok().build();
            }
        }

        throw new SettingNotFoundException("key: " + setting.getKey());
    }

    @DeleteMapping("{key}")
    public ResponseEntity<Object> deleteSetting(@PathVariable Integer userId, @PathVariable String key) {
        User user = getUser(userId);

        for (Setting s : user.getSettings()) {
            if (s.getKey().equals(key)) {
                settingRepository.delete(s);
                return ResponseEntity.ok().build();
            }
        }

        throw new SettingNotFoundException("key: " + key);
    }
}
