package com.example.boardPrompt.post.application;

import com.example.boardPrompt.post.domain.Post;
import com.example.boardPrompt.post.domain.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class PostReadServiceTest {

    private final PostRepository postRepository = mock(PostRepository.class);
    private final PostReadService postReadService = new PostReadService(postRepository);

    @Test
    @DisplayName("게시글 목록을 조회하면 모든 게시글이 반환된다")
    void getAll_returnsAllPosts() {
        // given
        Post post1 = new Post(1L, "제목1", "내용1");
        Post post2 = new Post(2L, "제목2", "내용2");
        given(postRepository.findAll()).willReturn(List.of(post1, post2));

        // when
        List<PostReadResult> results = postReadService.getAll();

        // then
        assertThat(results).hasSize(2);
        assertThat(results.get(0).id()).isEqualTo(1L);
        assertThat(results.get(0).title()).isEqualTo("제목1");
        assertThat(results.get(0).content()).isEqualTo("내용1");
        assertThat(results.get(1).id()).isEqualTo(2L);
    }

    @Test
    @DisplayName("ID로 게시글을 조회하면 해당 게시글이 반환된다")
    void getById_returnsPost() {
        // given
        Post post = new Post(1L, "제목", "내용");
        given(postRepository.findById(1L)).willReturn(Optional.of(post));

        // when
        PostReadResult result = postReadService.getById(1L);

        // then
        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.title()).isEqualTo("제목");
        assertThat(result.content()).isEqualTo("내용");
    }

    @Test
    @DisplayName("존재하지 않는 ID로 조회하면 예외가 발생한다")
    void getById_throwsWhenNotFound() {
        // given
        given(postRepository.findById(anyLong())).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> postReadService.getById(999L))
                .isInstanceOf(PostNotFoundException.class);
    }
}

