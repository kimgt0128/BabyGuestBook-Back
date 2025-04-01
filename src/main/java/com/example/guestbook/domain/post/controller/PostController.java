package com.example.guestbook.domain.post.controller;

import com.example.guestbook.domain.post.dto.request.CreatePostRequest;
import com.example.guestbook.domain.post.dto.request.DeletePostRequest;
import com.example.guestbook.domain.post.dto.response.PostResponse;
import com.example.guestbook.domain.post.dto.request.UpdatePostRequest;
import com.example.guestbook.domain.post.service.PostService;
import com.example.guestbook.global.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    @GetMapping
    public ApiResponse<List<PostResponse>> readAll() {
        return ApiResponse.success(postService.readAll(), "게시글이 정상적으로 조회되었습니다.");
    }

    @PostMapping("/post")
    public ApiResponse create(@RequestBody CreatePostRequest req) {
        postService.create(req);
        return ApiResponse.success("게시글이 정상적으로 생성되었습니다.");
    }

    @PatchMapping("/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ApiResponse updatePost(
            @PathVariable Long postId,
            @RequestBody @Valid UpdatePostRequest req
    ) {
        postService.update(postId, req);
        return ApiResponse.success(postId + "번 게시글이 정상적으로 수정되었습니다.");
    }

    @DeleteMapping("/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ApiResponse delete(
            @PathVariable Long postId,
            @RequestBody @Valid DeletePostRequest req
    ) {
        postService.delete(postId, req);
        return ApiResponse.success(postId + "번 게시글이 정상적으로 삭제되었습니다.");
    }
}