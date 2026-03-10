package com.example.boardPrompt.post.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Post {

    private Long id;
    private String title;
    private String content;

    public static Post create(String title, String content) {
        return Post.builder()
                .title(title)
                .content(content)
                .build();
    }
}
