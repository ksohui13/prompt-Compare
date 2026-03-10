package com.example.boardPrompt.post.infrastructure;

import com.example.boardPrompt.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostJpaRepository extends JpaRepository<Post, Long> {
}
