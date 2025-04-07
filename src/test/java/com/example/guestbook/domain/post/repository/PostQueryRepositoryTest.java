package com.example.guestbook.domain.post.repository;

import com.example.guestbook.domain.comment.entity.Comment;
import com.example.guestbook.domain.comment.repository.CommentRepository;
import com.example.guestbook.domain.post.dto.response.PostResponse;
import com.example.guestbook.domain.post.entity.Emotion;
import com.example.guestbook.domain.post.entity.Post;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

@ActiveProfiles("test")
@SpringBootTest
class PostQueryRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostQueryRepository postQueryRepository;

    @AfterEach
    void tearDown() {
        commentRepository.deleteAllInBatch();
        postRepository.deleteAllInBatch();
    }

    @Test
    void findAllInfiniteScrollOrderById() {
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

        // when
        long lastPostId = posts.get(posts.size() - 1).getId() - 5;
        List<PostResponse> result = postQueryRepository.findAllInfiniteScrollOrderById(
                lastPostId,
                Emotion.HAPPINESS,
                3L
        );

        // then
        List<Tuple> expected = posts.stream()
                .filter(post -> post.getId() < lastPostId)
                .sorted((o1, o2) -> (int) (o2.getId() - o1.getId()))
                .limit(3)
                .map(i -> tuple(i.getId(), i.getEmotion()))
                .toList();

        assertThat(result)
                .hasSize(3)
                .extracting("postId", "emotion")
                .containsExactlyElementsOf(expected);
    }

    @Test
    void findAllInfiniteScrollOrderByCommentCnt1() {
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
                Emotion.HAPPINESS,
                3L
        );

        // then
        List<Tuple> expected = posts.stream()
                .sorted((o1, o2) -> (int) (o2.getId() - o1.getId()))
                .limit(3)
                .map(i -> tuple(i.getId(), i.getEmotion(), i.getId()))
                .toList();

        assertThat(result)
                .hasSize(3)
                .extracting("postId", "emotion", "commentCnt")
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
                Emotion.SADNESS,
                3L
        );

        // then
        assertThat(result).isEmpty();
    }
}