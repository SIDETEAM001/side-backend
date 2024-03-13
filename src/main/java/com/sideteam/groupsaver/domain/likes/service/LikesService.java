package com.sideteam.groupsaver.domain.likes.service;

import com.sideteam.groupsaver.domain.likes.domain.Likes;
import com.sideteam.groupsaver.domain.likes.dto.request.LikesRequestDto;
import com.sideteam.groupsaver.domain.likes.repository.LikesRepository;
import com.sideteam.groupsaver.domain.member.domain.Member;
import com.sideteam.groupsaver.domain.member.repository.MemberRepository;
import com.sideteam.groupsaver.domain.qna.domain.Qna;
import com.sideteam.groupsaver.domain.qna.repository.QnaRepository;
import com.sideteam.groupsaver.domain.qna_reply.repository.QnaReplyRepository;
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



    public Object likesQna(Long id, LikesRequestDto likesRequestDto) {
        /* 게시글 조회 */
        Qna qna = qnaRepository.findById(likesRequestDto.getQna().getId())
                .orElseThrow(() -> new IllegalArgumentException("QNA 게시글이 없습니다 ! : " + likesRequestDto.getQna().getId()));

        /* 사용자 조회 */
        Member member = memberRepository.findById(likesRequestDto.getMember().getId())
                .orElseThrow(() -> new IllegalArgumentException("회원이 없습니다 ! : " +likesRequestDto.getMember().getId()));

        /* 좋아요가 있는지 확인 */
        boolean likeState = likesRepository.existsByMemberIdAndQnaId(member.getId(), qna.getId());

        if (likeState) {
            Likes likes = likesRepository.findByMemberIdAndQnaId(member.getId(), qna.getId())
                    .orElseThrow(() -> new IllegalArgumentException("좋아요가 없습니다 !"));
            likesRepository.delete(likes);
            return null;
        } else {
            Likes likes = Likes.of(likesRequestDto);
            return likesRepository.save(likes);
        }
    }
}
