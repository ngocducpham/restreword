package com.example.restreword.controller;

import com.example.restreword.entity.NoticeMessage;
import com.example.restreword.repo.NoticeMessageRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
public class AclController {
    @Autowired
    private NoticeMessageRepository messageRepository;

    @GetMapping("api/msg")
    public List<NoticeMessage> getAllMessage() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        log.warn("authentication: {}", authentication.getName());
        return messageRepository.findAll();
    }

    @PutMapping("api/msg")
    public NoticeMessage updateMessage(@RequestBody NoticeMessage message) {
        return messageRepository.save(message);
    }

    @PostMapping("api/msg")
    public NoticeMessage save(NoticeMessage message) {
        return messageRepository.save(message);
    }

    @DeleteMapping("api/msg/{id}")
    public void delete(@PathVariable Long id) {
        messageRepository.deleteById(id);
    }
}
