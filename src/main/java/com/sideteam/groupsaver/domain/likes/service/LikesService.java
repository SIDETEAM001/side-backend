package com.sideteam.groupsaver.domain.likes.service;

import com.sideteam.groupsaver.domain.likes.domain.Likes;
import com.sideteam.groupsaver.domain.likes.dto.request.LikesRequestDto;
import com.sideteam.groupsaver.domain.likes.repository.LikesRepository;
import com.sideteam.groupsaver.domain.member.domain.Member;
import com.sideteam.groupsaver.domain.member.repository.MemberRepository;
import com.sideteam.groupsaver.domain.qna.domain.Qna;
import com.sideteam.groupsaver.domain.qna.repository.QnaRepository;
import com.sideteam.groupsaver.global.exception.BusinessException;
import com.sideteam.groupsaver.global.exception.member.MemberErrorCode;
import com.sideteam.groupsaver.global.exception.qna.QnaErrorCode;
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


    public Likes addLikesQna(LikesRequestDto likesRequestDto, Long memberId) {

        // 이미 사용자가 해당 게시글에 좋아요를 눌렀는지 확인
        boolean alreadyLikes = likesRepository.existsByMemberIdAndQnaId(memberId, likesRequestDto.getQnaId());

        if (alreadyLikes) {
            throw new BusinessException(QnaErrorCode.LIKE_ALREADY_EXIST, "좋아요가 이미 있습니다");
        }

        // 사용자 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(MemberErrorCode.MEMBER_NOT_FOUND, "멤버 정보를 가져올 수 없습니다."));

        // 게시글 조회
        Qna qna = qnaRepository.findById(likesRequestDto.getQnaId())
                .orElseThrow(() -> new BusinessException(QnaErrorCode.QNA_NOT_FOUND, "게시글을 찾을 수 없습니다"));

        // 좋아요 저장
        Likes likes = Likes.of(memberRepository.getReferenceById(memberId), qna);
        return likesRepository.save(likes);

    }

    /* 좋아요 취소 */
    public void deleteLikesQna(Long id, Long memberId) {
        // 좋아요를 찾습니다.
        Likes likes = likesRepository.findById(id)
                .orElseThrow(() -> new BusinessException(QnaErrorCode.LIKE_NOT_FOUND, "좋아요를 찾을 수 없습니다"));

        // 좋아요 사용자와 취소 요청 사용자의 ID를 비교합니다.
        if (!likes.getMember().getId().equals(memberId)) {
            throw new BusinessException(QnaErrorCode.LIKE_MEMBER_NOT_MATCH, "사용자가 일치하지 않습니다");
        }

        likesRepository.delete(likes);
    }
}
