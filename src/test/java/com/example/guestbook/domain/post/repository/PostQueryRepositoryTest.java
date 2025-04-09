package com.example.guestbook.domain.post.repository;

import com.example.guestbook.domain.comment.entity.Comment;
import com.example.guestbook.domain.comment.repository.CommentRepository;
import com.example.guestbook.domain.like.entity.Like;
import com.example.guestbook.domain.like.repository.LikeRepository;
import com.example.guestbook.domain.member.entity.Member;
import com.example.guestbook.domain.member.entity.Role;
import com.example.guestbook.domain.member.repository.MemberRepository;
import com.example.guestbook.domain.post.dto.response.PostResponse;
import com.example.guestbook.domain.post.entity.Emotion;
import com.example.guestbook.domain.post.entity.Post;
import com.example.guestbook.domain.post.service.EmotionAnalyser;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

@ActiveProfiles("test")
@SpringBootTest
class PostQueryRepositoryTest {

    @MockitoBean
    EmotionAnalyser emotionAnalyser;

    @Autowired
    PostRepository postRepository;

    @Autowired
    LikeRepository likeRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostQueryRepository postQueryRepository;
    @Autowired
    private MemberRepository memberRepository;

    @AfterEach
    void tearDown() {
        commentRepository.deleteAllInBatch();
        likeRepository.deleteAllInBatch();
        postRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
    }

    @Test
    void findAllInfiniteScrollOrderById() {
        // given
        Member member = createMember();

        List<Post> posts = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Post post = Post.builder()
                    .content("content" + i)
                    .username("user" + i)
                    .password("password")
                    .emotion(Emotion.HAPPINESS)
                    .build();

            posts.add(post);

        }

        postRepository.saveAll(posts);

        for (Post post : posts) {
            if (isEvenId(post.getId())) {
                Like like = new Like(member.getId(), post.getId());
                likeRepository.save(like);
            }
        }

        // when
        long lastPostId = posts.get(posts.size() - 1).getId() - 5;
        List<PostResponse> result = postQueryRepository.findAllInfiniteScrollOrderById(
                member.getId(),
                lastPostId,
                Emotion.HAPPINESS,
                3L
        );

        // then
        List<Tuple> expected = posts.stream()
                .filter(post -> post.getId() < lastPostId)
                .sorted((o1, o2) -> (int) (o2.getId() - o1.getId()))
                .limit(3)
                .map(i -> tuple(i.getId(), i.getEmotion(), isEvenId(i.getId()) ? 1L : 0L, isEvenId(i.getId())))
                .toList();

        assertThat(result)
                .hasSize(3)
                .extracting("postId", "emotion", "likeCnt", "isLike")
                .containsExactlyElementsOf(expected);
    }

    private Member createMember() {
        Member member = Member.builder()
                .email("test@test.com")
                .role(Role.USER)
                .nickname("tester")
                .registerType("google")
                .socialId(1234L)
                .build();

        return memberRepository.save(member);
    }

    private boolean isEvenId(Long id) {
        return id % 2 == 0;
    }

    @Test
    void findAllInfiniteScrollOrderByCommentCnt1() {
        // given
        Member member = createMember();

        List<Post> posts = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Post post = Post.builder()
                    .content("content" + i)
                    .username("user" + i)
                    .password("password")
                    .emotion(Emotion.HAPPINESS)
                    .build();

            posts.add(post);
        }

        postRepository.saveAll(posts);

        for (Post post : posts) {
            for (int i = 0; i < post.getId(); i++) {
                Comment comment = Comment.builder()
                        .postId(post.getId())
                        .content("content" + i)
                        .username("user" + i)
                        .password("password")
                        .build();

                commentRepository.save(comment);
            }

            if (isEvenId(post.getId())) {
                Like like = new Like(member.getId(), post.getId());
                likeRepository.save(like);
            }
        }

        // when
        List<PostResponse> result = postQueryRepository.findAllInfiniteScrollOrderByCommentCnt(
                member.getId(),
                Emotion.HAPPINESS,
                3L
        );

        // then
        List<Tuple> expected = posts.stream()
                .sorted((o1, o2) -> (int) (o2.getId() - o1.getId()))
                .limit(3)
                .map(i -> tuple(i.getId(), i.getEmotion(), i.getId(), isEvenId(i.getId()) ? 1L : 0L, isEvenId(i.getId())))
                .toList();

        assertThat(result)
                .hasSize(3)
                .extracting("postId", "emotion", "commentCnt", "likeCnt", "isLike")
                .containsExactlyElementsOf(expected);
    }

    @Test
    void findAllInfiniteScrollOrderByCommentCnt2() {
        // given
        List<Post> posts = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Post post = Post.builder()
                    .content("content" + i)
                    .username("user" + i)
                    .password("password")
                    .emotion(Emotion.HAPPINESS)
                    .build();

            posts.add(post);
        }

        postRepository.saveAll(posts);

        for (Post post : posts) {
            for (int i = 0; i < post.getId(); i++) {
                Comment comment = Comment.builder()
                        .postId(post.getId())
                        .content("content" + i)
                        .username("user" + i)
                        .password("password")
                        .build();

                commentRepository.save(comment);
            }
        }

        // when
        List<PostResponse> result = postQueryRepository.findAllInfiniteScrollOrderByCommentCnt(
                null,
                Emotion.SADNESS,
                3L
        );

        // then
        assertThat(result).isEmpty();
    }
}