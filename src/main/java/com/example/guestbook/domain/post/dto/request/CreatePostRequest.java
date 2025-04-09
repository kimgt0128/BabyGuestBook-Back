package com.example.guestbook.domain.post.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
}
