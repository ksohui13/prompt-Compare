package com.example.boardPrompt.post.application;

import com.example.boardPrompt.post.application.port.PostRepositoryPort;
import com.example.boardPrompt.post.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostUpdateService {

    private final PostRepositoryPort postRepositoryPort;

    public Optional<Post> update(Long id, String title, String content) {
        return postRepositoryPort.update(id, title, content);
    }
}
