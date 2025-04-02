package com.example.guestbook.domain.post.service;


import com.example.guestbook.domain.post.dto.request.CreatePostRequest;
import com.example.guestbook.domain.post.dto.request.DeletePostRequest;
import com.example.guestbook.domain.post.dto.request.UpdatePostRequest;
import com.example.guestbook.domain.post.dto.response.PostResponse;
import com.example.guestbook.domain.post.entity.Post;
import com.example.guestbook.domain.post.exception.PostErrorCode;
import com.example.guestbook.domain.post.repository.PostRepository;
import com.example.guestbook.global.error.exception.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;

    public List<PostResponse> readAll() {
        List<Post> posts = postRepository.findAll();



        return posts.stream()
                .map(PostResponse::from)
                .toList();

    }

    @Transactional
    public void create(CreatePostRequest req) {
        Post entity = req.toEntity();
        postRepository.save(entity);
    }

    @Transactional
    public void update(Long postId, UpdatePostRequest req) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(PostErrorCode.NOT_FOUND));

        this.checkPassword(req.getPassword(), post.getPassword());

        post.update(req.getContent());
    }

    @Transactional
    public void delete(Long postId, DeletePostRequest req) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(PostErrorCode.NOT_FOUND));

        this.checkPassword(req.getPassword(), post.getPassword());
    }

    private void checkPassword(String password, String expectedPassword) {
        if (!password.equals(expectedPassword)) {
            throw new NotFoundException(PostErrorCode.INVALID_PASSWORD);
        }
    }

}
