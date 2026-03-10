package com.example.boardPrompt.post.application.port;

import com.example.boardPrompt.post.domain.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepositoryPort {

    Post save(Post post);

    List<Post> findAll();

    Optional<Post> findById(Long id);

    Optional<Post> update(Long id, String title, String content);
}
