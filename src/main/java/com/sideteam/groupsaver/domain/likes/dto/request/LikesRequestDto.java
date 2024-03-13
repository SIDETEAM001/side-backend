package com.sideteam.groupsaver.domain.likes.dto.request;

import com.sideteam.groupsaver.domain.member.domain.Member;
import com.sideteam.groupsaver.domain.qna.domain.Qna;
import lombok.Getter;

@Getter
public class LikesRequestDto {

    private Qna qna;
    private Member member;

}
