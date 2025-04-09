package com.example.guestbook.domain.comment.dto.request;

import com.example.guestbook.domain.comment.entity.Comment;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateCommentRequest {
    private String content;
    private String password;

    @Builder
    public CreateCommentRequest(String content, String password) {
        this.content = content;
        this.password = password;
    }

    public Comment toEntity(Long postId, String username, PasswordEncoder passwordEncoder) {
        return Comment.builder()
                .content(content)
                .username(username)
                .password(passwordEncoder.encode(password))
                .postId(postId)
                .build();
    }
}
