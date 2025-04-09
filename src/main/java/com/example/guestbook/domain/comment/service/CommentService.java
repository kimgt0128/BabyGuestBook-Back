package com.example.guestbook.domain.comment.service;

import com.example.guestbook.domain.comment.dto.request.CreateCommentRequest;
import com.example.guestbook.domain.comment.dto.request.DeleteCommentRequest;
import com.example.guestbook.domain.comment.dto.request.UpdateCommentRequest;
import com.example.guestbook.domain.comment.dto.response.CommentResponse;
import com.example.guestbook.domain.comment.entity.Comment;
import com.example.guestbook.domain.comment.exception.CommentErrorCode;
import com.example.guestbook.domain.comment.repository.CommentRepository;
import com.example.guestbook.global.error.exception.BadRequestException;
import com.example.guestbook.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PasswordEncoder passwordEncoder;
    private final CommentRepository commentRepository;

    public List<CommentResponse> readAll(Long postId) {
        return commentRepository.findAllByPostId(postId).stream()
                .map(CommentResponse::from)
                .toList();
    }

    @Transactional
    public void create(Long postId, String username, CreateCommentRequest req) {
        Comment comment = req.toEntity(postId, username, passwordEncoder);
        commentRepository.save(comment);
    }

    @Transactional
    public void update(Long postId, Long commentId, UpdateCommentRequest req) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException(CommentErrorCode.NOT_FOUND));

        // 비밀번호 일치 확인
        this.checkPassword(req.getPassword(), comment.getPassword());

        comment.update(req.getContent());
    }

    @Transactional
    public void delete(Long postId, Long commentId, DeleteCommentRequest req) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException(CommentErrorCode.NOT_FOUND));

        // 비밀번호 일치 확인
        this.checkPassword(req.getPassword(), comment.getPassword());

        commentRepository.delete(comment);
    }

    private void checkPassword(String password, String expectedPassword) {
        if (!passwordEncoder.matches(password, expectedPassword)) {
            throw new BadRequestException(CommentErrorCode.INVALID_PASSWORD);
        }
    }
}
