package com.example.boardPrompt.post.application;

import com.example.boardPrompt.post.application.port.PostRepositoryPort;
import com.example.boardPrompt.post.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostCreateService {

    private final PostRepositoryPort postRepositoryPort;

    public Post create(String title, String content) {
        Post post = Post.create(title, content);
        return postRepositoryPort.save(post);
    }
}
