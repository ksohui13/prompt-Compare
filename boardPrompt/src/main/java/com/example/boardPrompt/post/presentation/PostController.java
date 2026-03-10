package com.example.boardPrompt.post.presentation;

import com.example.boardPrompt.post.application.PostCreateService;
import com.example.boardPrompt.post.application.PostDeleteService;
import com.example.boardPrompt.post.application.PostFindService;
import com.example.boardPrompt.post.application.PostUpdateService;
import com.example.boardPrompt.post.domain.Post;
import com.example.boardPrompt.post.presentation.dto.PostCreateRequest;
import com.example.boardPrompt.post.presentation.dto.PostResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostCreateService postCreateService;
    private final PostFindService postFindService;
    private final PostUpdateService postUpdateService;
    private final PostDeleteService postDeleteService;

    @PostMapping
    public ResponseEntity<PostResponse> create(@Valid @RequestBody PostCreateRequest request) {
        Post post = postCreateService.create(request.getTitle(), request.getContent());
        return ResponseEntity.status(HttpStatus.CREATED).body(PostResponse.from(post));
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getList() {
        List<PostResponse> list = postFindService.getList().stream()
                .map(PostResponse::from)
                .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getById(@PathVariable Long id) {
        return postFindService.getById(id)
                .map(PostResponse::from)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> update(@PathVariable Long id,
                                               @Valid @RequestBody PostCreateRequest request) {
        return postUpdateService.update(id, request.getTitle(), request.getContent())
                .map(PostResponse::from)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (postDeleteService.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
