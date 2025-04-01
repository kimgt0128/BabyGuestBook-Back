package com.example.guestbook.domain.post.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class DeletePostRequest {
    private String password;

    public DeletePostRequest(String password) {
        this.password = password;
    }
}
