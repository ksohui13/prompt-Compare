package com.example.boardPrompt.post.application;

import com.example.boardPrompt.post.application.port.PostRepositoryPort;
import com.example.boardPrompt.post.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostFindService {

    private final PostRepositoryPort postRepositoryPort;

    public List<Post> getList() {
        return postRepositoryPort.findAll();
    }

    public Optional<Post> getById(Long id) {
        return postRepositoryPort.findById(id);
    }
}
