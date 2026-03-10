package com.example.boardPrompt.post.application;

import com.example.boardPrompt.post.domain.Post;
import com.example.boardPrompt.post.domain.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostCreateService {

    private final PostRepository postRepository;

    public PostCreateService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Transactional
    public PostCreateResult create(String title, String content) {
        Post post = new Post(null, title, content);
        Post saved = postRepository.save(post);
        return new PostCreateResult(saved.getId(), saved.getTitle(), saved.getContent());
    }
}

