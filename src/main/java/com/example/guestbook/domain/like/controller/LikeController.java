package com.example.guestbook.domain.like.controller;

import com.example.guestbook.domain.like.service.LikeService;
import com.example.guestbook.global.auth.dto.OAuthUserImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/posts/{postId}")
@Controller
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/like")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void like(@PathVariable("postId") Long postId,
                     @AuthenticationPrincipal OAuthUserImpl oAuthUser) {
        Long memberId = oAuthUser.getMember().getId();
        likeService.like(memberId, postId);
    }

    @DeleteMapping("/like")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unlike(@PathVariable("postId") Long postId,
                       @AuthenticationPrincipal OAuthUserImpl oAuthUser) {
        Long memberId = oAuthUser.getMember().getId();
        likeService.unlike(memberId, postId);
    }
}
