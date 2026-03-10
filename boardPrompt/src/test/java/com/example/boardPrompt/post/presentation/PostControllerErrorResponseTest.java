package com.example.boardPrompt.post.presentation;

import com.example.boardPrompt.post.application.PostCreateService;
import com.example.boardPrompt.post.application.PostDeleteService;
import com.example.boardPrompt.post.application.PostReadService;
import com.example.boardPrompt.post.application.PostUpdateService;
import com.example.boardPrompt.post.application.PostNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
class PostControllerErrorResponseTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PostCreateService postCreateService;

    @MockBean
    PostReadService postReadService;

    @MockBean
    PostUpdateService postUpdateService;

    @MockBean
    PostDeleteService postDeleteService;

    @Test
    @DisplayName("게시글이 없을 때 공통 404 에러 응답을 반환한다")
    void getById_returnsCommonNotFoundErrorResponse() throws Exception {
        // given
        given(postReadService.getById(anyLong()))
                .willThrow(new PostNotFoundException(999L));

        // when & then
        mockMvc.perform(get("/api/posts/{id}", 999L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("게시글을 찾을 수 없습니다. id=999"))
                .andExpect(jsonPath("$.path").value("/api/posts/999"));
    }

    @Test
    @DisplayName("Validation 실패 시 공통 400 에러 응답을 반환한다")
    void create_returnsCommonBadRequestErrorResponse_whenValidationFails() throws Exception {
        // title, content 모두 누락
        String json = """
                {
                }
                """;

        mockMvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("요청 값이 유효하지 않습니다."))
                .andExpect(jsonPath("$.path").value("/api/posts"))
                .andExpect(jsonPath("$.errors").isArray());
    }
}

