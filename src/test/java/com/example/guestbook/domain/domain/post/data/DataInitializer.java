package com.example.guestbook.domain.domain.post.data;

import com.example.guestbook.domain.post.entity.Emotion;
import com.example.guestbook.domain.post.entity.Post;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Disabled
@SpringBootTest
public class DataInitializer {

    @PersistenceContext
    EntityManager em;

    @Autowired
    TransactionTemplate tx;

    static int BULK_INSERT_SIZE = 1000;
    static final int EXECUTION_COUNT = 100;

    @Test
    void init() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(EXECUTION_COUNT);

        for (int i = 0; i < EXECUTION_COUNT; i++) {
            executorService.submit(() -> {
                insert();
                latch.countDown();
                System.out.println("latch.getCount() = " + latch.getCount());
            });
        }

        latch.await();
        executorService.shutdown();
    }

    void insert() {
        tx.executeWithoutResult(transactionStatus -> {
            for (int i = 0; i < BULK_INSERT_SIZE; i++) {
                Post post = Post.builder()
                        .content("content" + i)
                        .username("user" + i)
                        .password("password")
                        .emotion(Emotion.values()[i % Emotion.values().length])
                        .build();

                em.persist(post);
            }
        });
    }
}
