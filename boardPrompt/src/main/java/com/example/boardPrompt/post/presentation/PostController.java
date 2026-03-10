package com.example.boardPrompt.post.presentation;

import com.example.boardPrompt.post.application.PostCreateService;
import com.example.boardPrompt.post.domain.Post;
import com.example.boardPrompt.post.presentation.dto.PostCreateRequest;
import com.example.boardPrompt.post.presentation.dto.PostResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostCreateService postCreateService;

    @PostMapping
    public ResponseEntity<PostResponse> create(@Valid @RequestBody PostCreateRequest request) {
        Post post = postCreateService.create(request.getTitle(), request.getContent());
        return ResponseEntity.status(HttpStatus.CREATED).body(PostResponse.from(post));
    }
}
