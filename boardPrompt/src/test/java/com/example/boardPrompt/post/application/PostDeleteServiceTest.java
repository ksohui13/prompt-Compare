package com.example.boardPrompt.post.application;

import com.example.boardPrompt.post.application.port.PostRepositoryPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("PostDeleteService")
class PostDeleteServiceTest {

    @Mock
    private PostRepositoryPort postRepositoryPort;

    @InjectMocks
    private PostDeleteService postDeleteService;

    @Test
    @DisplayName("존재하는 id 삭제 시 true를 반환한다")
    void delete_whenExists_returnsTrue() {
        when(postRepositoryPort.deleteById(1L)).thenReturn(true);

        boolean result = postDeleteService.delete(1L);

        assertThat(result).isTrue();
        verify(postRepositoryPort).deleteById(1L);
    }

    @Test
    @DisplayName("없는 id 삭제 시 false를 반환한다")
    void delete_whenNotExists_returnsFalse() {
        when(postRepositoryPort.deleteById(999L)).thenReturn(false);

        boolean result = postDeleteService.delete(999L);

        assertThat(result).isFalse();
        verify(postRepositoryPort).deleteById(999L);
    }
}
