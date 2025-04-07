package com.example.guestbook.domain.comment.dto.response;

import com.example.guestbook.domain.comment.entity.Comment;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentResponse {

    private Long commentId;
    private String content;
    private String username;
    private LocalDateTime updatedAt;

    @Builder
    public CommentResponse(Long commentId, String content, String username, LocalDateTime updatedAt) {
        this.commentId = commentId;
        this.content = content;
        this.username = username;
        this.updatedAt = updatedAt;
    }

    public static CommentResponse from(Comment comment) {
        return CommentResponse.builder()
                .commentId(comment.getId())
                .content(comment.getContent())
                .updatedAt(comment.getUpdatedAt())
                .username(comment.getUsername())
                .build();
    }
}
