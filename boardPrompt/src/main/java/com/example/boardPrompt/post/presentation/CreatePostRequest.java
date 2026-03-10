package com.example.boardPrompt.post.presentation;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreatePostRequest {

    @NotBlank(message = "제목은 필수입니다")
    private String title;

    @NotBlank(message = "내용은 필수입니다")
    private String content;

    public CreatePostRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
