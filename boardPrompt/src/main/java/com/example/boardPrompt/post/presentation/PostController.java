package com.example.boardPrompt.post.presentation;

import com.example.boardPrompt.post.application.CreatePostUseCase;
import com.example.boardPrompt.post.application.DeletePostUseCase;
import com.example.boardPrompt.post.application.GetPostUseCase;
import com.example.boardPrompt.post.application.UpdatePostUseCase;
import com.example.boardPrompt.post.domain.Post;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final CreatePostUseCase createPostUseCase;
    private final GetPostUseCase getPostUseCase;
    private final UpdatePostUseCase updatePostUseCase;
    private final DeletePostUseCase deletePostUseCase;

    @PostMapping
    public ResponseEntity<PostResponse> create(@Valid @RequestBody CreatePostRequest request) {
        Post post = createPostUseCase.create(request.getTitle(), request.getContent());
        return ResponseEntity.status(HttpStatus.CREATED).body(new PostResponse(post));
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAll() {
        List<Post> posts = getPostUseCase.getAll();
        List<PostResponse> responses = posts.stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getById(@PathVariable Long id) {
        Post post = getPostUseCase.getById(id);
        return ResponseEntity.ok(new PostResponse(post));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> update(@PathVariable Long id,
                                               @Valid @RequestBody UpdatePostRequest request) {
        Post post = updatePostUseCase.update(id, request.getTitle(), request.getContent());
        return ResponseEntity.ok(new PostResponse(post));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deletePostUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }
}

