package com.example.guestbook.domain.post.service;


import com.example.guestbook.domain.post.dto.request.CreatePostRequest;
import com.example.guestbook.domain.post.dto.request.DeletePostRequest;
import com.example.guestbook.domain.post.dto.request.UpdatePostRequest;
import com.example.guestbook.domain.post.entity.Post;
import com.example.guestbook.domain.post.exception.PostErrorCode;
import com.example.guestbook.domain.post.repository.PostRepository;
import com.example.guestbook.global.error.GlobalExceptionHandler;
import com.example.guestbook.global.error.exception.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.InvalidObjectException;

@RequiredArgsConstructor

@Service
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public void create(CreatePostRequest req) {
        // 엔티티 변환
        Post entity = req.toEntity();
        postRepository.save(entity);
    }

    @Transactional
    public void update(Long postId, UpdatePostRequest req) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(PostErrorCode.NOT_FOUND));

        // 비밀번호 일치 확인
        this.checkPassword(req.getPassword(), post.getPassword());

        post.update(req.getContent());
        postRepository.save(post);
    }

    @Transactional
    public void delete(Long postId, DeletePostRequest req) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(PostErrorCode.NOT_FOUND));

        //비밀번호 일치 확인
        this.checkPassword(req.getPassword(), post.getPassword());
    }

    private void checkPassword(String password, String expectedPassword) {
        if (!password.equals(expectedPassword)) {
            //throw new InvalidObjectException(PostErrorCode.INVALID_PASSWORD.getCode());
            //throw new BadRequestException();
        }
    }
}
