package com.example.boardPrompt.post.presentation;

import com.example.boardPrompt.post.application.PostCreateService;
import com.example.boardPrompt.post.application.PostDeleteService;
import com.example.boardPrompt.post.application.PostReadService;
import com.example.boardPrompt.post.application.PostUpdateService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
class PostControllerValidationTest {

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
    @DisplayName("게시글 등록 시 title이 누락되면 400을 반환한다")
    void create_shouldFail_whenTitleMissing() throws Exception {
        String json = """
                {
                  "content": "내용만 있는 경우"
                }
                """;

        mockMvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].field").value("title"));
    }

    @Test
    @DisplayName("게시글 등록 시 content가 누락되면 400을 반환한다")
    void create_shouldFail_whenContentMissing() throws Exception {
        String json = """
                {
                  "title": "제목만 있는 경우"
                }
                """;

        mockMvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].field").value("content"));
    }

    @Test
    @DisplayName("게시글 수정 시 title이 누락되면 400을 반환한다")
    void update_shouldFail_whenTitleMissing() throws Exception {
        String json = """
                {
                  "content": "수정된 내용"
                }
                """;

        mockMvc.perform(put("/api/posts/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].field").value("title"));
    }

    @Test
    @DisplayName("게시글 수정 시 content가 누락되면 400을 반환한다")
    void update_shouldFail_whenContentMissing() throws Exception {
        String json = """
                {
                  "title": "수정된 제목"
                }
                """;

        mockMvc.perform(put("/api/posts/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].field").value("content"));
    }
}

