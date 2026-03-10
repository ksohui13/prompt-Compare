package com.example.boardPrompt.post.application.port;

import com.example.boardPrompt.post.domain.Post;

public interface PostRepositoryPort {

    Post save(Post post);
}
