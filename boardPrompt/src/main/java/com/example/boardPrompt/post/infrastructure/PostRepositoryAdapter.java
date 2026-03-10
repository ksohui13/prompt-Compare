package com.example.boardPrompt.post.infrastructure;

import com.example.boardPrompt.post.application.port.PostRepositoryPort;
import com.example.boardPrompt.post.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostRepositoryAdapter implements PostRepositoryPort {

    private final PostJpaRepository postJpaRepository;

    @Override
    public Post save(Post post) {
        PostJpaEntity entity = PostJpaEntity.from(post.getTitle(), post.getContent());
        PostJpaEntity saved = postJpaRepository.save(entity);
        return Post.builder()
                .id(saved.getId())
                .title(saved.getTitle())
                .content(saved.getContent())
                .build();
    }
}
