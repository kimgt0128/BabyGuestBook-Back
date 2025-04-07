package com.example.guestbook.domain.like.service;

import jakarta.transaction.Transactional;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Service;


@Service
public class LikeService {

    @Transactional
    public void like(Long memberId, Long postId) {

    }
}
