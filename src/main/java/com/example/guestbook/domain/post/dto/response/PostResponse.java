package com.example.guestbook.domain.post.dto.response;

import com.example.guestbook.domain.post.entity.Post;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostResponse {

    private Long postId;
    private String content;
    private String username;
    private String emotion;
    private LocalDateTime updatedAt;

    @Builder
    public PostResponse(Long postId, String content, String emotion, String username, LocalDateTime updatedAt) {
        this.postId = postId;
        this.content = content;
        this.emotion = emotion;
        this.username = username;
        this.updatedAt = updatedAt;

    }


    public static PostResponse from(Post post) {
        return PostResponse.builder()
                .postId(post.getId())
                .content(post.getContent())
                .emotion(post.getEmotion())
                .username(post.getUsername())
                .updatedAt(post.getUpdatedAt())
                .build();
    }
}
