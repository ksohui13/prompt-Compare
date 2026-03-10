package com.example.boardPrompt.post.application;

import com.example.boardPrompt.post.application.port.PostRepositoryPort;
import com.example.boardPrompt.post.domain.Post;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("PostCreateService")
class PostCreateServiceTest {

    @Mock
    private PostRepositoryPort postRepositoryPort;

    @InjectMocks
    private PostCreateService postCreateService;

    @Test
    @DisplayName("게시글을 생성하면 저장된 Post를 반환한다")
    void create_returnsSavedPost() {
        String title = "제목";
        String content = "내용";
        Post saved = Post.builder().id(1L).title(title).content(content).build();
        when(postRepositoryPort.save(any(Post.class))).thenReturn(saved);

        Post result = postCreateService.create(title, content);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getTitle()).isEqualTo(title);
        assertThat(result.getContent()).isEqualTo(content);
        verify(postRepositoryPort).save(any(Post.class));
    }
}
