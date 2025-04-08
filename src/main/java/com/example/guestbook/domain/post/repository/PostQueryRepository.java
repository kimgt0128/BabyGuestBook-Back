package com.example.guestbook.domain.post.repository;

import com.example.guestbook.domain.post.dto.response.PostResponse;
import com.example.guestbook.domain.post.entity.Emotion;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.guestbook.domain.comment.entity.QComment.comment;
import static com.example.guestbook.domain.like.entity.QLike.like;
import static com.example.guestbook.domain.post.entity.QPost.post;

@Repository
@RequiredArgsConstructor
public class PostQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<PostResponse> findAllInfiniteScrollOrderById(Long currentMemberId, Long lastPostId, Emotion emotion, Long pageSize) {
        // currentMemberId가 null일 경우 isLike는 항상 false, 아니면 CASE 조건으로 계산
        Expression<Boolean> isLikeExpression = this.getIsLike(currentMemberId);

        return queryFactory
                .select(
                        Projections.fields(PostResponse.class,
                                post.id.as("postId"),
                                post.content,
                                post.username,
                                post.emotion,
                                comment.count().as("commentCnt"),
                                post.updatedAt,
                                like.id.countDistinct().as("likeCnt"),
                                ExpressionUtils.as(isLikeExpression, "isLike")
                        )
                )
                .from(post)
                .leftJoin(comment).on(comment.postId.eq(post.id))
                .leftJoin(like).on(like.postId.eq(post.id))
                .where(
                        this.olderThanLastPostId(lastPostId),
                        this.postEmotionIs(emotion)
                )
                .groupBy(post.id)
                .orderBy(post.id.desc())
                .limit(pageSize)
                .fetch();
    }

    public List<PostResponse> findAllInfiniteScrollOrderByCommentCnt(Long currentMemberId, Emotion emotion, Long pageSize) {
        Expression<Boolean> isLikeExpression = this.getIsLike(currentMemberId);

        return queryFactory
                .select(
                        Projections.fields(PostResponse.class,
                                post.id.as("postId"),
                                post.content,
                                post.username,
                                post.emotion,
                                comment.countDistinct().as("commentCnt"),
                                post.updatedAt,
                                like.id.countDistinct().as("likeCnt"),
                                ExpressionUtils.as(isLikeExpression, "isLike")
                        )
                )
                .from(post)
                .leftJoin(comment).on(comment.postId.eq(post.id))
                .leftJoin(like).on(like.postId.eq(post.id))
                .where(this.postEmotionIs(emotion))
                .groupBy(post.id)
                .orderBy(comment.countDistinct().desc(), post.id.desc())
                .limit(pageSize)
                .fetch();
    }

    private Expression<Boolean> getIsLike(Long currentMemberId) {
        Expression<Boolean> isLikeExpression;
        if (currentMemberId == null) {
            isLikeExpression = Expressions.constant(false);
        } else {
            isLikeExpression = Expressions.booleanTemplate(
                    "COALESCE(sum(case when {0} = {1} then 1 else 0 end), 0) > 0",
                    like.memberId, currentMemberId
            );
        }
        return isLikeExpression;
    }

    private BooleanExpression olderThanLastPostId(Long lastPostId) {
        return lastPostId != null ? post.id.lt(lastPostId) : null;
    }

    private BooleanExpression postEmotionIs(Emotion emotion) {
        return emotion != null ? post.emotion.eq(emotion) : null;
    }
}
