package com.example.boardPrompt.post.presentation;

import com.example.boardPrompt.post.application.CreatePostUseCase;
import com.example.boardPrompt.post.domain.Post;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final CreatePostUseCase createPostUseCase;

    @PostMapping
    public ResponseEntity<PostResponse> create(@Valid @RequestBody CreatePostRequest request) {
        Post post = createPostUseCase.create(request.getTitle(), request.getContent());
        return ResponseEntity.status(HttpStatus.CREATED).body(new PostResponse(post));
    }
}
