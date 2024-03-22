package com.sideteam.groupsaver.domain.likes.service;

import com.sideteam.groupsaver.domain.likes.domain.Likes;
import com.sideteam.groupsaver.domain.likes.dto.request.LikesRequestDto;
import com.sideteam.groupsaver.domain.likes.repository.LikesRepository;
import com.sideteam.groupsaver.domain.member.domain.Member;
import com.sideteam.groupsaver.domain.member.repository.MemberRepository;
import com.sideteam.groupsaver.domain.qna.domain.Qna;
import com.sideteam.groupsaver.domain.qna.repository.QnaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class LikesService {

    private final MemberRepository memberRepository;
    private final QnaRepository qnaRepository;
    private final LikesRepository likesRepository;


    public Object addLikesQna(LikesRequestDto likesRequestDto, Long memberId) {
        /* 게시글 조회 */
        Qna qna = qnaRepository.findById(likesRequestDto.getQnaId())
                .orElseThrow(() -> new IllegalArgumentException("QNA 게시글이 없습니다 ! : " + likesRequestDto.getQnaId()));

        /* 사용자 조회 */
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원이 없습니다 ! : " + memberId));

        // 이미 사용자가 해당 게시글에 좋아요를 눌렀는지 확인
        boolean alreadyLikes = likesRepository.existsByMemberIdAndQnaId(memberId, qna.getId());

        if (alreadyLikes) {
            throw new IllegalStateException("이미 좋아요가 있습니다 !");
        } else {
            Likes likes = Likes.of(memberRepository.getReferenceById(memberId), qna);
            return likesRepository.save(likes);
        }
    }

    /* 좋아요 취소 */
    public void deleteLikesQna(Long id, Long memberId) {
        // 좋아요를 찾습니다.
        Likes likes = likesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("좋아요를 찾을 수 없습니다. ID: " + id));

        // 좋아요 사용자와 취소 요청 사용자의 ID를 비교합니다.
        if (!likes.getMember().getId().equals(memberId)) {
            throw new IllegalArgumentException("사용자가 일치하지 않습니다 !");
        }

        likesRepository.delete(likes);
    }
}
