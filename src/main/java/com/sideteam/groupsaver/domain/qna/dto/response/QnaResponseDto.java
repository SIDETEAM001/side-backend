package com.sideteam.groupsaver.domain.qna.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sideteam.groupsaver.domain.qna.domain.Qna;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QnaResponseDto {

    private Long id;
    private String title;
    private String body;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private Instant createAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private Instant updateAt;

    /* Entity -> ResponseDTO 변환 */
    public static QnaResponseDto toDto(Qna qna) {
        QnaResponseDto responseDto = new QnaResponseDto();
        responseDto.setId(qna.getId());
        responseDto.setTitle(qna.getTitle());
        responseDto.setBody(qna.getBody());
        responseDto.setCreateAt(qna.getCreateAt());
        responseDto.setUpdateAt(qna.getUpdateAt());
        return responseDto;
    }

}
