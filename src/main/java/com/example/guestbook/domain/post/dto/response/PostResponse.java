package com.example.guestbook.domain.post.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class PostResponse {

    private Long postId;
    private String content;
    //User 추가
    private LocalDateTime updatedAt;


}
