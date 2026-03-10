package com.example.boardPrompt.post.application;

import com.example.boardPrompt.post.domain.Post;
import com.example.boardPrompt.post.domain.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostDeleteService {

    private final PostRepository postRepository;

    public PostDeleteService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Transactional
    public void delete(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));
        postRepository.delete(post);
    }
}

