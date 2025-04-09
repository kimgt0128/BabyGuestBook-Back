package com.example.guestbook.domain.post.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Table(name = "posts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @Enumerated(EnumType.STRING)
    private Emotion emotion;
    private String username;
    private String password;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder
    public Post(String content, Emotion emotion, String username, String password) {
        this.content = content;
        this.emotion = emotion;
        this.username = username;
        this.password = password;
    }

    public static Post of(String content, Emotion emotion, String username, String password) {
        return Post.builder()
                .content(content)
                .emotion(emotion)
                .username(username)
                .password(password)
                .build();
    }

    public void update(String content, Emotion emotion) {
        this.content = content;
        this.emotion = emotion;
    }

}
