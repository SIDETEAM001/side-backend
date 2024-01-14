package com.sideteam.groupsaver.domain.qna.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sideteam.groupsaver.domain.qna.domain.Qna;
import lombok.*;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class QnaResponseDto {

    private Long id;
    private String title;
    private String body;

    /* Entity -> ResponseDTO 변환 */
    public static QnaResponseDto toDto(Qna qna) {
        QnaResponseDto responseDto = new QnaResponseDto();
        responseDto.setId(qna.getId());
        responseDto.setTitle(qna.getTitle());
        responseDto.setBody(qna.getBody());
        return responseDto;
    }

    public static List<QnaResponseDto> listOf(List<Qna> qnaList) {
        return qnaList.stream()
                .map(QnaResponseDto::toDto)
                .collect(Collectors.toList());
    }

}
