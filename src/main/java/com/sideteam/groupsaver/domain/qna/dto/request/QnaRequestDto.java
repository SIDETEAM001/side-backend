package com.sideteam.groupsaver.domain.qna.dto.request;

import com.sideteam.groupsaver.domain.qna.domain.Qna;
import com.sideteam.groupsaver.domain.qna.dto.response.QnaResponseDto;
import lombok.*;

import java.time.Instant;

@Getter
public class QnaRequestDto {
    private String title;
    private String body;

    /* RequestDTO -> Entity 변환 */
    public static Qna toEntity(QnaRequestDto qnaRequestDto) {
        Qna qna = new Qna();
        qna.setTitle(qnaRequestDto.getTitle());
        qna.setBody(qnaRequestDto.getBody());
        return qna;
    }
}
