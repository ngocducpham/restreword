package com.example.restreword.repo;

import com.example.restreword.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Integer> {
    Optional<List<Post>> findByUserId(Integer userId);
}


