package com.example.guestbook.domain.like.service;

import com.example.guestbook.domain.like.entity.Like;
import com.example.guestbook.domain.like.exception.LikeErrorCode;
import com.example.guestbook.domain.like.repository.LikeRepository;
import com.example.guestbook.domain.post.exception.PostErrorCode;
import com.example.guestbook.domain.post.repository.PostRepository;
import com.example.guestbook.global.error.exception.BadRequestException;
import com.example.guestbook.global.error.exception.NotFoundException;
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
            throw new NotFoundException(PostErrorCode.NOT_FOUND);
        }

        // 이미 좋아요 눌렀는지 확인
        Optional<Like> isExist = likeRepository.findByMemberIdAndPostId(memberId, postId);

        if(isExist.isPresent()) {
            throw new BadRequestException(LikeErrorCode.ALREADY_LIKED);
        }
        likeRepository.save(new Like(memberId, postId));
    }

    @Transactional
    public void unlike(Long memberId, Long postId) {
        Optional<Like> isExist = likeRepository.findByMemberIdAndPostId(memberId, postId);

        // 좋아요가 존재할 경우에만 취소
        if(isExist.isPresent()) {
            likeRepository.delete(isExist.get());
            return;
        }
        throw new NotFoundException(LikeErrorCode.NOT_FOUND);

    }
}
