package com.example.guestbook.domain.post.controller;

import com.example.guestbook.domain.post.dto.request.CreatePostRequest;
import com.example.guestbook.domain.post.dto.request.DeletePostRequest;
import com.example.guestbook.domain.post.dto.request.ReadPostParameter;
import com.example.guestbook.domain.post.dto.request.UpdatePostRequest;
import com.example.guestbook.domain.post.dto.response.PostResponse;
import com.example.guestbook.domain.post.entity.Emotion;
import com.example.guestbook.domain.post.service.PostService;
import com.example.guestbook.global.auth.provider.OAuthUserImpl;
import com.example.guestbook.global.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    @GetMapping
    public ApiResponse<List<PostResponse>> readAll(
            @RequestParam(value = "order", defaultValue = "LATEST") ReadPostParameter.Order order,
            @RequestParam(value = "emotion", required = false) Emotion emotion,
            @RequestParam(value = "pageSize", defaultValue = "10") Long pageSize,
            @RequestParam(value = "lastPostId", required = false) Long lastPostId,
            @AuthenticationPrincipal OAuthUserImpl loginUser
    ) {
        Long loginMemberId = Optional.ofNullable(loginUser)
                .map(oAuthUser -> oAuthUser.getMember().getId())
                .orElse(null);

        return ApiResponse.success(
                postService.readAllInfiniteScroll(loginMemberId, ReadPostParameter.of(order, emotion, pageSize, lastPostId)),
                "게시글이 정상적으로 조회되었습니다."
        );
    }

    @PostMapping
    public ApiResponse create(
            @AuthenticationPrincipal OAuthUserImpl loginUser,
            @RequestBody @Valid CreatePostRequest req
    ) {
        String username = Optional.ofNullable(loginUser)
                .map(OAuthUserImpl::getName)
                .orElse("익명");

        postService.create(username, req);
        return ApiResponse.success("게시글이 정상적으로 생성되었습니다.");
    }

    @PatchMapping("/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(
            @PathVariable("postId") Long postId,
            @RequestBody @Valid UpdatePostRequest req
    ) {
        postService.update(postId, req);
    }

    @DeleteMapping("/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable("postId") Long postId,
            @RequestBody @Valid DeletePostRequest req
    ) {
        postService.delete(postId, req);
    }
}