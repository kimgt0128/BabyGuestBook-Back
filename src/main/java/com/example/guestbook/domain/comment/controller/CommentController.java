package com.example.guestbook.domain.comment.controller;

import com.example.guestbook.domain.comment.dto.request.CreateCommentRequest;
import com.example.guestbook.domain.comment.dto.request.DeleteCommentRequest;
import com.example.guestbook.domain.comment.dto.request.UpdateCommentRequest;
import com.example.guestbook.domain.comment.dto.response.CommentResponse;
import com.example.guestbook.domain.comment.service.CommentService;
import com.example.guestbook.global.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/posts/{postId}/comments")
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public ApiResponse<List<CommentResponse>> readAll(@PathVariable("postId") Long postId) {
        return ApiResponse.success(commentService.readAll(postId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Object> create(
            @PathVariable("postId") Long postId,
            @RequestBody @Valid CreateCommentRequest req
    ) {
        commentService.create(postId, req);
        return ApiResponse.success("댓글 작성 성공");
    }

    @PatchMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateContent(
            @PathVariable("postId") Long postId,
            @PathVariable("commentId") Long commentId,
            @RequestBody @Valid UpdateCommentRequest req
    ) {
        commentService.update(postId, commentId, req);
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable("postId") Long postId,
            @PathVariable("commentId") Long commentId,
            @RequestBody @Valid DeleteCommentRequest req
    ) {
        commentService.delete(postId, commentId, req);
    }
}
