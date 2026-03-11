package com.example.boardPrompt.post.application;

import com.example.boardPrompt.post.infrastructure.PostJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DeletePostUseCase {

    private final PostJpaRepository postJpaRepository;

    public void delete(Long id) {
        if (!postJpaRepository.existsById(id)) {
            throw new IllegalArgumentException("게시글을 찾을 수 없습니다. id=" + id);
        }
        postJpaRepository.deleteById(id);
    }
}
