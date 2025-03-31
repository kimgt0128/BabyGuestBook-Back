package com.example.guestbook.domain.post.controller;

import com.example.guestbook.domain.post.dto.CreatePostRequest;
import com.example.guestbook.domain.post.dto.DeletePostRequest;
import com.example.guestbook.domain.post.dto.PostResponse;
import com.example.guestbook.domain.post.dto.UpdatePostRequest;
import com.example.guestbook.domain.post.service.PostService;
import com.example.guestbook.global.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor

@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    //Create
    @PostMapping("/post")
    public ApiResponse create(@RequestBody CreatePostRequest requ) {
        PostResponse res = postService.create(requ);
        return ApiResponse.success(res, "게시글이 정상적으로 삭제되었습니다.");
    }

    //Update
    @PatchMapping("/{postId}")
    public ApiResponse updatePost(
            @PathVariable Long postId,
            @RequestBody @Valid UpdatePostRequest req
    ) {

        PostResponse res = postService.update(postId, req);

        return ApiResponse.success(res, postId + "번 게시글이 정상적으로 수정되었습니다.");
    }

    //Delete
    @DeleteMapping("/{postId}")
    public ApiResponse delete(
            @PathVariable Long postId,
            @RequestBody @Valid DeletePostRequest req
    ) {

        PostResponse res = postService.delete(postId, req);
        return ApiResponse.success(res, postId + "번 게시글이 정상적으로 삭제되었습니다.");
    }
}
