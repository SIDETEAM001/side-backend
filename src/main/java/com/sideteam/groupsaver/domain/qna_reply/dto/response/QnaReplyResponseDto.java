package com.sideteam.groupsaver.domain.qna_reply.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sideteam.groupsaver.domain.qna_reply.domain.QnaReply;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class QnaReplyResponseDto {

    private Long id;
    private String body;
    private String nickName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private Instant createAt;
    private Long parentReplyId;

    /* Entity -> DTO 변환 */
    @Builder
    public static QnaReplyResponseDto toDto(QnaReply qnaReply) {
        /* 부모 댓글이 존재하면 부모 댓글 ID, 그렇지 않으면 null */
        Long parentReplyId = (qnaReply.getParentReply() != null) ? qnaReply.getParentReply().getId() : null;
        return QnaReplyResponseDto.builder()
                .id(qnaReply.getId())
                .body(qnaReply.getBody())
                .nickName(qnaReply.getCreator().getNickname())
                .createAt(qnaReply.getCreator().getCreateAt())
                .parentReplyId(parentReplyId)
                .build();
    }

    public static List<QnaReplyResponseDto> listOf(List<QnaReply> replyList) {
        return replyList.stream()
                .map(QnaReplyResponseDto::toDto)
                .collect(Collectors.toList());
    }
}