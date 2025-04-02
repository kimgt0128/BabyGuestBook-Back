package com.example.guestbook.domain.post.dto.request;

import com.example.guestbook.domain.post.entity.Emotion;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReadPostParameter {

    private Order order = Order.LATEST;
    private Emotion emotion;
    private Long pageSize = 10L;
    private Long lastPostId;

    public ReadPostParameter(Order order, Emotion emotion, Long pageSize, Long lastPostId) {
        this.order = order != null ? order : Order.LATEST;
        this.emotion = emotion;
        this.pageSize = pageSize != null ? pageSize : 10L;
        this.lastPostId = lastPostId;
    }

    public static ReadPostParameter of(Order order, Emotion emotion, Long pageSize, Long lastPostId) {
        return new ReadPostParameter(order, emotion, pageSize, lastPostId);
    }

    public enum Order {
        LATEST, COMMENT
    }
}
