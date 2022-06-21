package com.example.restreword.service;

import com.example.restreword.dto.PostInput;
import com.example.restreword.dto.PostOutput;
import com.example.restreword.dto.mapper.PostMapper;
import com.example.restreword.entity.Post;
import com.example.restreword.entity.User;
import com.example.restreword.exception.UserNotFoundException;
import com.example.restreword.repo.PostRepository;
import com.example.restreword.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostMapper mapper;

    public List<PostOutput> findAll(Integer id) {
        return mapper.toOutputDtos(postRepository.findByUserId(id)
                .orElseThrow(() -> new UserNotFoundException("User not found")));
    }

    public PostOutput findOne(Integer postId) {
        return mapper.toOutputDto(postRepository.findById(postId)
                .orElseThrow(() -> new UserNotFoundException("Post not found")));

    }

    public Integer create(Integer userId, PostInput postInput) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Post post = mapper.inputToEntity(postInput);
        post.setUser(user);
        return postRepository.saveAndFlush(post).getId();
    }

    public void update(Integer postId, PostInput postInput) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new UserNotFoundException("Post not found"));
        post.setDescription(postInput.getDest());
        postRepository.saveAndFlush(post);
    }

    public void delete(Integer postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new UserNotFoundException("Post not found"));
        postRepository.delete(post);
    }
}
