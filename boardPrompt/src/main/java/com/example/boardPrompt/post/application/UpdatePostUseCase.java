package com.example.boardPrompt.post.application;

import com.example.boardPrompt.post.domain.Post;
import com.example.boardPrompt.post.infrastructure.PostJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UpdatePostUseCase {

    private final PostJpaRepository postJpaRepository;

    public Post update(Long id, String title, String content) {
        Post post = postJpaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다. id=" + id));
        post.update(title, content);
        return post;
    }
}

