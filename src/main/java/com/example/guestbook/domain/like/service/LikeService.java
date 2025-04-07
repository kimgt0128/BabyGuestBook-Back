package com.example.guestbook.domain.like.service;

import com.example.guestbook.domain.like.entity.Likes;
import com.example.guestbook.domain.like.repository.LikeRepository;
import com.example.guestbook.domain.post.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LikeService {
    private final LikeRepository likeRepository;
    private final PostRepository postRepository;

    @Transactional
    public void like(Long memberId, Long postId) {
        // 게시물 존재 확인
        if(!postRepository.existsById(postId)) {
            return;
        }

        // 이미 좋아요 눌렀는지 확인
        Optional<Likes> isExist = likeRepository.findByMemberIdAndPostId(memberId, postId);

        if(isExist.isPresent()) {
            return;
        }
        likeRepository.save(new Likes(memberId, postId));
    }

    @Transactional
    public void unlike(Long memberId, Long postId) {
        Optional<Likes> isExist = likeRepository.findByMemberIdAndPostId(memberId, postId);
        if(isExist.isPresent()) {
            likeRepository.delete(isExist.get());
            return;
        }

    }
}
