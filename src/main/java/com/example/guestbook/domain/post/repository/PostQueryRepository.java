package com.example.guestbook.domain.post.repository;

import com.example.guestbook.domain.post.dto.response.PostResponse;
import com.example.guestbook.domain.post.entity.Emotion;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.guestbook.domain.comment.entity.QComment.comment;
import static com.example.guestbook.domain.post.entity.QPost.post;

@Repository
@RequiredArgsConstructor
public class PostQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<PostResponse> findAllInfiniteScrollOrderById(Long lastPostId, Emotion emotion, Long pageSize) {
        return queryFactory
                .select(
                        Projections.fields(PostResponse.class,
                                post.id.as("postId"),
                                post.content,
                                post.username,
                                post.emotion,
                                comment.count().as("commentCnt"),
                                post.updatedAt
                        )
                )
                .from(post)
                .leftJoin(comment).on(comment.postId.eq(post.id))
                .where(
                        this.olderThanLastPostId(lastPostId),
                        this.postEmotionIs(emotion)
                )
                .groupBy(post.id)
                .orderBy(post.id.desc())
                .limit(pageSize)
                .fetch();
    }

    public List<PostResponse> findAllInfiniteScrollOrderByCommentCnt(Emotion emotion, Long pageSize) {
        return queryFactory
                .select(
                        Projections.fields(PostResponse.class,
                                post.id.as("postId"),
                                post.content,
                                post.username,
                                post.emotion,
                                comment.countDistinct().as("commentCnt"),
                                post.updatedAt
                        )
                )
                .from(post)
                .leftJoin(comment).on(comment.postId.eq(post.id))
                .where(this.postEmotionIs(emotion))
                .groupBy(post.id)
                .orderBy(comment.countDistinct().desc(), post.id.desc())
                .limit(pageSize)
                .fetch();
    }

    private BooleanExpression olderThanLastPostId(Long lastPostId) {
        return lastPostId != null ? post.id.lt(lastPostId) : null;
    }

    private BooleanExpression postEmotionIs(Emotion emotion) {
        return emotion != null ? post.emotion.eq(emotion) : null;
    }
}
