package com.example.boardPrompt.post.infrastructure;

import com.example.boardPrompt.post.application.port.PostRepositoryPort;
import com.example.boardPrompt.post.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PostRepositoryAdapter implements PostRepositoryPort {

    private final PostJpaRepository postJpaRepository;

    @Override
    public Post save(Post post) {
        PostJpaEntity entity = PostJpaEntity.from(post.getTitle(), post.getContent());
        PostJpaEntity saved = postJpaRepository.save(entity);
        return toPost(saved);
    }

    @Override
    public List<Post> findAll() {
        return postJpaRepository.findAll().stream()
                .map(this::toPost)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Post> findById(Long id) {
        return postJpaRepository.findById(id).map(this::toPost);
    }

    @Override
    public Optional<Post> update(Long id, String title, String content) {
        return postJpaRepository.findById(id)
                .map(entity -> {
                    entity.updateTitleAndContent(title, content);
                    return toPost(postJpaRepository.save(entity));
                });
    }

    private Post toPost(PostJpaEntity entity) {
        return Post.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .build();
    }
}
