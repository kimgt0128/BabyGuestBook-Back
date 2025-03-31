package com.example.guestbook.domain.post.service;


import com.example.guestbook.domain.post.dto.CreatePostRequest;
import com.example.guestbook.domain.post.dto.DeletePostRequest;
import com.example.guestbook.domain.post.dto.PostResponse;
import com.example.guestbook.domain.post.dto.UpdatePostRequest;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    public PostResponse create(CreatePostRequest request) {
        return null;
    }

    public PostResponse update(Long postId, UpdatePostRequest req) {
        return null;
    }

    public PostResponse delete(Long postId, DeletePostRequest req) {
        return null;
    }
}
