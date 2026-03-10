package com.example.boardPrompt.post.presentation;

import com.example.boardPrompt.post.application.PostCreateResult;
import com.example.boardPrompt.post.application.PostCreateService;
import com.example.boardPrompt.post.application.PostReadResult;
import com.example.boardPrompt.post.application.PostReadService;
import com.example.boardPrompt.post.application.PostUpdateResult;
import com.example.boardPrompt.post.application.PostUpdateService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostCreateService postCreateService;
    private final PostReadService postReadService;
    private final PostUpdateService postUpdateService;

    public PostController(PostCreateService postCreateService, PostReadService postReadService, PostUpdateService postUpdateService) {
        this.postCreateService = postCreateService;
        this.postReadService = postReadService;
        this.postUpdateService = postUpdateService;
    }

    @PostMapping
    public ResponseEntity<PostCreateResponse> create(@Valid @RequestBody PostCreateRequest request) {
        PostCreateResult result = postCreateService.create(request.title(), request.content());
        PostCreateResponse response = new PostCreateResponse(result.id(), result.title(), result.content());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<PostReadResponse>> getAll() {
        List<PostReadResponse> responses = postReadService.getAll().stream()
                .map(result -> new PostReadResponse(result.id(), result.title(), result.content()))
                .toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostReadResponse> getById(@PathVariable Long id) {
        PostReadResult result = postReadService.getById(id);
        PostReadResponse response = new PostReadResponse(result.id(), result.title(), result.content());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostUpdateResponse> update(@PathVariable Long id,
                                                     @Valid @RequestBody PostUpdateRequest request) {
        PostUpdateResult result = postUpdateService.update(id, request.title(), request.content());
        PostUpdateResponse response = new PostUpdateResponse(result.id(), result.title(), result.content());
        return ResponseEntity.ok(response);
    }
}

