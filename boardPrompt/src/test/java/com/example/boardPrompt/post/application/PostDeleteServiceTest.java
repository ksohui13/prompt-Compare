package com.example.boardPrompt.post.application;

import com.example.boardPrompt.post.domain.Post;
import com.example.boardPrompt.post.domain.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class PostDeleteServiceTest {

    private final PostRepository postRepository = mock(PostRepository.class);
    private final PostDeleteService postDeleteService = new PostDeleteService(postRepository);

    @Test
    @DisplayName("존재하는 게시글을 삭제하면 리포지토리 delete가 호출된다")
    void delete_success_whenPostExists() {
        // given
        Post existing = new Post(1L, "제목", "내용");
        given(postRepository.findById(1L)).willReturn(Optional.of(existing));

        // when
        postDeleteService.delete(1L);

        // then
        verify(postRepository).findById(anyLong());
        verify(postRepository).delete(existing);
    }
}

