package com.sideteam.groupsaver.domain.qna_reply.dto.request;

import com.sideteam.groupsaver.domain.qna.domain.Qna;
import lombok.Getter;

@Getter
public class QnaReplyRequestDto {

    private String body;
    private Qna qna;
    private Long parentReplyId;

}