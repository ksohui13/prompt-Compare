package com.example.boardPrompt.post.application;

import com.example.boardPrompt.post.domain.Post;
import com.example.boardPrompt.post.domain.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class PostCreateServiceTest {

    private final PostRepository postRepository = mock(PostRepository.class);
    private final PostCreateService postCreateService = new PostCreateService(postRepository);

    @Test
    @DisplayName("title과 content가 모두 존재하면 게시글 등록에 성공한다")
    void createPost_success_whenTitleAndContentProvided() {
        // given
        String title = "제목";
        String content = "내용";

        Post saved = new Post(1L, title, content);
        given(postRepository.save(any(Post.class))).willReturn(saved);

        // when
        PostCreateResult result = postCreateService.create(title, content);

        // then
        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.title()).isEqualTo(title);
        assertThat(result.content()).isEqualTo(content);
        verify(postRepository).save(any(Post.class));
    }
}

