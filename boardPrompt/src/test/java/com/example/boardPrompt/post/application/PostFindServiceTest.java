package com.example.boardPrompt.post.application;

import com.example.boardPrompt.post.application.port.PostRepositoryPort;
import com.example.boardPrompt.post.domain.Post;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("PostFindService")
class PostFindServiceTest {

    @Mock
    private PostRepositoryPort postRepositoryPort;

    @InjectMocks
    private PostFindService postFindService;

    @Test
    @DisplayName("getList는 전체 목록을 반환한다")
    void getList_returnsAll() {
        List<Post> posts = List.of(
                Post.builder().id(1L).title("제목1").content("내용1").build(),
                Post.builder().id(2L).title("제목2").content("내용2").build()
        );
        when(postRepositoryPort.findAll()).thenReturn(posts);

        List<Post> result = postFindService.getList();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getTitle()).isEqualTo("제목1");
        assertThat(result.get(1).getTitle()).isEqualTo("제목2");
        verify(postRepositoryPort).findAll();
    }

    @Test
    @DisplayName("getById는 존재하는 id면 Post를 반환한다")
    void getById_whenExists_returnsPost() {
        Long id = 1L;
        Post post = Post.builder().id(id).title("제목").content("내용").build();
        when(postRepositoryPort.findById(id)).thenReturn(Optional.of(post));

        Optional<Post> result = postFindService.getById(id);

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(id);
        assertThat(result.get().getTitle()).isEqualTo("제목");
        verify(postRepositoryPort).findById(id);
    }

    @Test
    @DisplayName("getById는 없는 id면 empty를 반환한다")
    void getById_whenNotExists_returnsEmpty() {
        when(postRepositoryPort.findById(999L)).thenReturn(Optional.empty());

        Optional<Post> result = postFindService.getById(999L);

        assertThat(result).isEmpty();
        verify(postRepositoryPort).findById(999L);
    }
}
