package com.example.boardPrompt.post.application;

import com.example.boardPrompt.post.domain.Post;
import com.example.boardPrompt.post.domain.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class PostUpdateServiceTest {

    private final PostRepository postRepository = mock(PostRepository.class);
    private final PostUpdateService postUpdateService = new PostUpdateService(postRepository);

    @Test
    @DisplayName("존재하는 게시글의 제목과 내용을 수정하면 수정된 게시글이 반환된다")
    void update_success_whenPostExists() {
        // given
        Post existing = new Post(1L, "기존 제목", "기존 내용");
        given(postRepository.findById(1L)).willReturn(Optional.of(existing));

        String newTitle = "수정된 제목";
        String newContent = "수정된 내용";

        // when
        PostUpdateResult result = postUpdateService.update(1L, newTitle, newContent);

        // then
        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.title()).isEqualTo(newTitle);
        assertThat(result.content()).isEqualTo(newContent);
        verify(postRepository).findById(anyLong());
    }
}

