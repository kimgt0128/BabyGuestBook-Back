package com.example.guestbook.domain.post.dto.request;

import com.example.guestbook.domain.post.entity.Post;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreatePostRequest {
    private String content;
    private String username;
    private String password;

    @Builder
    public CreatePostRequest(String content, String username, String password) {
        this.content = content;
        this.username = username;
        this.password = password;
    }

    public Post toEntity(PasswordEncoder passwordEncoder) {
        return Post.builder()
                .content(content)
                .username(username)
                .password(passwordEncoder.encode(password))
                .build();
    }
}
