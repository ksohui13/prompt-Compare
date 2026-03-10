package com.example.boardPrompt.post.application;

import com.example.boardPrompt.post.domain.Post;
import com.example.boardPrompt.post.infrastructure.PostJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetPostUseCase {

    private final PostJpaRepository postJpaRepository;

    public List<Post> getAll() {
        return postJpaRepository.findAll();
    }

    public Post getById(Long id) {
        return postJpaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다. id=" + id));
    }
}

