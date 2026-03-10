package com.example.boardPrompt.post.application;

import com.example.boardPrompt.post.domain.Post;
import com.example.boardPrompt.post.domain.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PostReadService {

    private final PostRepository postRepository;

    public PostReadService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<PostReadResult> getAll() {
        return postRepository.findAll().stream()
                .map(post -> new PostReadResult(post.getId(), post.getTitle(), post.getContent()))
                .toList();
    }

    public PostReadResult getById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));
        return new PostReadResult(post.getId(), post.getTitle(), post.getContent());
    }
}

