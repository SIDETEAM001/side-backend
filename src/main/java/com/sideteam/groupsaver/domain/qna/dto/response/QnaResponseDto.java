package com.sideteam.groupsaver.domain.qna.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sideteam.groupsaver.domain.qna.domain.Qna;
import com.sideteam.groupsaver.domain.qna_reply.dto.response.QnaReplyResponseDto;
import lombok.*;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class QnaResponseDto {

    private Long id;
    private String title;
    private String body;
    private String nickName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private Instant createAt;
    private List<QnaReplyResponseDto> qnaReplyList; // 댓글 리스트

    /* Entity -> ResponseDTO 변환 */
    public static QnaResponseDto toDto(Qna qna) {
        QnaResponseDto responseDto = new QnaResponseDto();
        responseDto.setId(qna.getId());
        responseDto.setTitle(qna.getTitle());
        responseDto.setBody(qna.getBody());
        responseDto.setNickName(qna.getCreator().getNickname());
        responseDto.setCreateAt((qna.getCreator().getCreateAt()));
        /* 댓글 리스트가 null이 아니면 리스트를, null이면 null */
        if (qna.getQnaReplyList() != null) {
            responseDto.setQnaReplyList(QnaReplyResponseDto.listOf(qna.getQnaReplyList()));
        } else {
            responseDto.setQnaReplyList(Collections.emptyList()); // null이 아닌 빈 리스트로 반환
        }
        return responseDto;
    }

    public static List<QnaResponseDto> listOf(List<Qna> qnaList) {
        return qnaList.stream()
                .map(QnaResponseDto::toDto)
                .collect(Collectors.toList());
    }

}
