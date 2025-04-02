package com.example.guestbook.domain.post.repository;

import com.example.guestbook.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query(
            value = "select * from posts " +
                    "order by id desc limit :limit",
            nativeQuery = true
    )
    List<Post> findAllInfiniteScroll(@Param("limit") Long limit);

    @Query(
            value = "select * from posts " +
                    "where id < :lastPostId " +
                    "order by id desc limit :limit",
            nativeQuery = true
    )
    List<Post> findAllInfiniteScroll(@Param("limit") Long limit, @Param("lastPostId") Long lastPostId);
}
