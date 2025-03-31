package com.example.guestbook.domain.comment.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateCommentRequest {

    private String content;
    private String password;

    @Builder
    public UpdateCommentRequest(String content, String password) {
        this.content = content;
        this.password = password;
    }
}
