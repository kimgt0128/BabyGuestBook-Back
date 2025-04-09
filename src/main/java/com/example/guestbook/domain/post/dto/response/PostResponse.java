package com.example.guestbook.domain.post.dto.response;

import com.example.guestbook.domain.post.entity.Emotion;
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
    private Boolean isLike;
    private Long likeCnt;
    private Long commentCnt;
    private LocalDateTime updatedAt;

    @Builder
    public PostResponse(Long postId, String content, Emotion emotion, Boolean isLike, Long likeCnt, String username, Long commentCnt, LocalDateTime updatedAt) {
        this.postId = postId;
        this.content = content;
        this.emotion = emotion;
        this.isLike = isLike;
        this.likeCnt = likeCnt;
        this.username = username;
        this.commentCnt = commentCnt;
        this.updatedAt = updatedAt;
    }
}
