package com.example.boardPrompt.post.presentation;

import com.example.boardPrompt.post.application.PostCreateService;
import com.example.boardPrompt.post.application.PostDeleteService;
import com.example.boardPrompt.post.application.PostFindService;
import com.example.boardPrompt.post.application.PostUpdateService;
import com.example.boardPrompt.post.domain.Post;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PostController.class)
@AutoConfigureMockMvc(addFilters = false)
@DisplayName("PostController")
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PostCreateService postCreateService;

    @MockBean
    private PostFindService postFindService;

    @MockBean
    private PostUpdateService postUpdateService;

    @MockBean
    private PostDeleteService postDeleteService;

    @Test
    @DisplayName("POST /api/posts - 등록 성공 시 201과 생성된 게시글을 반환한다")
    void create_returns201AndPost() throws Exception {
        Post saved = Post.builder().id(1L).title("제목").content("내용").build();
        when(postCreateService.create("제목", "내용")).thenReturn(saved);

        String body = objectMapper.writeValueAsString(new RequestBody("제목", "내용"));

        mockMvc.perform(post("/api/posts")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("제목"))
                .andExpect(jsonPath("$.content").value("내용"));

        verify(postCreateService).create("제목", "내용");
    }

    @Test
    @DisplayName("POST /api/posts - title 누락 시 400을 반환한다")
    void create_whenTitleBlank_returns400() throws Exception {
        String body = objectMapper.writeValueAsString(new RequestBody("", "내용"));

        mockMvc.perform(post("/api/posts")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /api/posts - 목록 조회 시 200과 배열을 반환한다")
    void getList_returns200AndArray() throws Exception {
        List<Post> posts = List.of(
                Post.builder().id(1L).title("제목1").content("내용1").build()
        );
        when(postFindService.getList()).thenReturn(posts);

        mockMvc.perform(get("/api/posts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("제목1"));

        verify(postFindService).getList();
    }

    @Test
    @DisplayName("GET /api/posts/{id} - 존재하는 id면 200과 게시글을 반환한다")
    void getById_whenExists_returns200AndPost() throws Exception {
        Post post = Post.builder().id(1L).title("제목").content("내용").build();
        when(postFindService.getById(1L)).thenReturn(Optional.of(post));

        mockMvc.perform(get("/api/posts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("제목"))
                .andExpect(jsonPath("$.content").value("내용"));

        verify(postFindService).getById(1L);
    }

    @Test
    @DisplayName("GET /api/posts/{id} - 없는 id면 404를 반환한다")
    void getById_whenNotExists_returns404() throws Exception {
        when(postFindService.getById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/posts/999"))
                .andExpect(status().isNotFound());

        verify(postFindService).getById(999L);
    }

    @Test
    @DisplayName("PUT /api/posts/{id} - 수정 성공 시 200과 수정된 게시글을 반환한다")
    void update_whenExists_returns200AndPost() throws Exception {
        Post updated = Post.builder().id(1L).title("새 제목").content("새 내용").build();
        when(postUpdateService.update(eq(1L), eq("새 제목"), eq("새 내용"))).thenReturn(Optional.of(updated));

        String body = objectMapper.writeValueAsString(new RequestBody("새 제목", "새 내용"));

        mockMvc.perform(put("/api/posts/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("새 제목"))
                .andExpect(jsonPath("$.content").value("새 내용"));

        verify(postUpdateService).update(1L, "새 제목", "새 내용");
    }

    @Test
    @DisplayName("PUT /api/posts/{id} - 없는 id면 404를 반환한다")
    void update_whenNotExists_returns404() throws Exception {
        when(postUpdateService.update(eq(999L), eq("제목"), eq("내용"))).thenReturn(Optional.empty());

        String body = objectMapper.writeValueAsString(new RequestBody("제목", "내용"));

        mockMvc.perform(put("/api/posts/999")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isNotFound());

        verify(postUpdateService).update(999L, "제목", "내용");
    }

    @Test
    @DisplayName("DELETE /api/posts/{id} - 삭제 성공 시 204를 반환한다")
    void delete_whenExists_returns204() throws Exception {
        when(postDeleteService.delete(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/posts/1").with(csrf()))
                .andExpect(status().isNoContent());

        verify(postDeleteService).delete(1L);
    }

    @Test
    @DisplayName("DELETE /api/posts/{id} - 없는 id면 404를 반환한다")
    void delete_whenNotExists_returns404() throws Exception {
        when(postDeleteService.delete(999L)).thenReturn(false);

        mockMvc.perform(delete("/api/posts/999").with(csrf()))
                .andExpect(status().isNotFound());

        verify(postDeleteService).delete(999L);
    }

    @SuppressWarnings("unused")
    private static class RequestBody {
        public String title;
        public String content;

        RequestBody(String title, String content) {
            this.title = title;
            this.content = content;
        }
    }
}
