package com.example.boardPrompt.post.application;

import com.example.boardPrompt.post.application.port.PostRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostDeleteService {

    private final PostRepositoryPort postRepositoryPort;

    public boolean delete(Long id) {
        return postRepositoryPort.deleteById(id);
    }
}
