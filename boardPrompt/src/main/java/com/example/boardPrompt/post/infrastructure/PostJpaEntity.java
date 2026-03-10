package com.example.boardPrompt.post.infrastructure;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "posts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    public PostJpaEntity(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public static PostJpaEntity from(String title, String content) {
        return new PostJpaEntity(title, content);
    }

    public void updateTitleAndContent(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
