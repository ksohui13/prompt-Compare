package com.example.boardPrompt.post.application;

import com.example.boardPrompt.post.domain.Post;
import com.example.boardPrompt.post.domain.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostUpdateService {

    private final PostRepository postRepository;

    public PostUpdateService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Transactional
    public PostUpdateResult update(Long id, String title, String content) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));
        post.update(title, content);
        return new PostUpdateResult(post.getId(), post.getTitle(), post.getContent());
    }
}

