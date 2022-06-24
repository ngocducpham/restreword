package com.example.restreword.controller;

import com.example.restreword.common.ResponseTemplate;
import com.example.restreword.dto.SettingInput;
import com.example.restreword.service.SettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/{userId}/setting")
public class SettingController {
    private final SettingService settingService;

    @GetMapping
    public ResponseEntity<Object> retrieveAllSetting(@PathVariable Integer userId) {
        return ResponseTemplate.success(settingService.findAll(userId));

    }

    @PostMapping()
    public ResponseEntity<Object> createSetting(@PathVariable Integer userId, @Valid @RequestBody SettingInput settingInput) {
        HttpStatus status =settingService.create(userId, settingInput);
        if(status == HttpStatus.OK)
            return ResponseTemplate.success();
        else return ResponseTemplate.fail("False", status);
    }

    @PatchMapping()
    public ResponseEntity<Object> updateSetting(@PathVariable Integer userId, @RequestBody SettingInput setting) {
        settingService.update(userId, setting);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{key}")
    public ResponseEntity<Object> deleteSetting(@PathVariable Integer userId, @PathVariable String key) {
        settingService.delete(userId, key);
        return ResponseEntity.ok().build();
    }
}
