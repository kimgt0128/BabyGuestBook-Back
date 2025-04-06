package com.example.guestbook.domain.post.dto.request;

import com.example.guestbook.domain.post.entity.Emotion;
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
    private String password;

    @Builder
    public CreatePostRequest(String content, String password) {
        this.content = content;
        this.password = password;
    }

    public Post toEntity(String username, PasswordEncoder passwordEncoder) {
        return Post.builder()
                .content(content)
                .username(username)
                .emotion(Emotion.HAPPY)
                .password(passwordEncoder.encode(password))
                .build();
    }
}
