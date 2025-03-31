package com.example.guestbook.domain.comment.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeleteCommentRequest {

    private String password;

    public DeleteCommentRequest(String password) {
        this.password = password;
    }
}
