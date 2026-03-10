package com.example.boardPrompt.post.presentation;

import com.example.boardPrompt.post.application.PostCreateResult;
import com.example.boardPrompt.post.application.PostCreateService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostCreateService postCreateService;

    public PostController(PostCreateService postCreateService) {
        this.postCreateService = postCreateService;
    }

    @PostMapping
    public ResponseEntity<PostCreateResponse> create(@Valid @RequestBody PostCreateRequest request) {
        PostCreateResult result = postCreateService.create(request.title(), request.content());
        PostCreateResponse response = new PostCreateResponse(result.id(), result.title(), result.content());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

