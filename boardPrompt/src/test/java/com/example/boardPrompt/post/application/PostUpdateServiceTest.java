package com.example.boardPrompt.post.application;

import com.example.boardPrompt.post.application.port.PostRepositoryPort;
import com.example.boardPrompt.post.domain.Post;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("PostUpdateService")
class PostUpdateServiceTest {

    @Mock
    private PostRepositoryPort postRepositoryPort;

    @InjectMocks
    private PostUpdateService postUpdateService;

    @Test
    @DisplayName("존재하는 id로 수정하면 수정된 Post를 반환한다")
    void update_whenExists_returnsUpdatedPost() {
        Long id = 1L;
        String newTitle = "새 제목";
        String newContent = "새 내용";
        Post updated = Post.builder().id(id).title(newTitle).content(newContent).build();
        when(postRepositoryPort.update(id, newTitle, newContent)).thenReturn(Optional.of(updated));

        Optional<Post> result = postUpdateService.update(id, newTitle, newContent);

        assertThat(result).isPresent();
        assertThat(result.get().getTitle()).isEqualTo(newTitle);
        assertThat(result.get().getContent()).isEqualTo(newContent);
        verify(postRepositoryPort).update(id, newTitle, newContent);
    }

    @Test
    @DisplayName("없는 id로 수정하면 empty를 반환한다")
    void update_whenNotExists_returnsEmpty() {
        when(postRepositoryPort.update(999L, "제목", "내용")).thenReturn(Optional.empty());

        Optional<Post> result = postUpdateService.update(999L, "제목", "내용");

        assertThat(result).isEmpty();
        verify(postRepositoryPort).update(999L, "제목", "내용");
    }
}
