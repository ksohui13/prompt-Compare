package com.example.boardPrompt.post.application;

import com.example.boardPrompt.post.domain.Post;
import com.example.boardPrompt.post.infrastructure.PostJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CreatePostUseCase {

    private final PostJpaRepository postJpaRepository;

    public Post create(String title, String content) {
        Post post = new Post(title, content);
        return postJpaRepository.save(post);
    }
}
