package com.example.guestbook.domain.post.dto.response;

import com.example.guestbook.domain.post.entity.Post;
import lombok.AccessLevel;
import lombok.Builder;
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

    @Builder
    public PostResponse(Long postId, String content, LocalDateTime updatedAt) {
        this.postId = postId;
        this.content = content;
        this.updatedAt = updatedAt;

    }


    public static PostResponse from(Post post) {
        return PostResponse.builder()
                .postId(post.getId())
                .content(post.getContent())
                .updatedAt(post.getUpdatedAt())
                //.user(new User(post.getUsername())
                .build();
    }
}
