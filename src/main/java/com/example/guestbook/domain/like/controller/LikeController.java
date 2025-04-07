package com.example.guestbook.domain.like.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/posts/{postId}")
@Controller
public class LikeController {

    @PostMapping("/like")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void like(@PathVariable("postId") Long postId) {

    }

    @DeleteMapping("/like")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unlike(@PathVariable("postId") Long postId) {

    }
}
