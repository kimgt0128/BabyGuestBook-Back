package com.example.guestbook.domain.comment.dto.response;

import com.example.guestbook.domain.comment.entity.Comment;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentResponse {

    private Long commentId;
    private String content;
    private User user;
    private LocalDateTime updatedAt;

    @Builder
    public CommentResponse(Long commentId, String content, User user, LocalDateTime updatedAt) {
        this.commentId = commentId;
        this.content = content;
        this.user = user;
        this.updatedAt = updatedAt;
    }

    public static CommentResponse from(Comment comment) {
        return CommentResponse.builder()
                .commentId(comment.getId())
                .content(comment.getContent())
                .updatedAt(comment.getUpdatedAt())
                .user(new User(comment.getUsername()))
                .build();
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class User {
        //        private Long userId;
        private String username;

    }
}
