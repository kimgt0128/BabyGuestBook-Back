package com.example.guestbook.domain.post.dto.response;

import com.example.guestbook.domain.post.entity.Emotion;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PostResponse {

    private Long postId;
    private String content;
    private String username;
    private Emotion emotion;
    private Long commentCnt;
    private LocalDateTime updatedAt;

    @Builder
    public PostResponse(Long postId, String content, Emotion emotion, String username, Long commentCnt, LocalDateTime updatedAt) {
        this.postId = postId;
        this.content = content;
        this.emotion = emotion;
        this.username = username;
        this.commentCnt = commentCnt;
        this.updatedAt = updatedAt;
    }
}
