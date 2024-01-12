package com.sideteam.groupsaver.domain.board.dto;

import com.sideteam.groupsaver.domain.board.domain.Board;
import lombok.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardResponseDto {
    private Long id;
    private String regId;
    private String title;
    private String content;
    private String createdDate;
    private String updatedDate;

    @Builder
    public static BoardResponseDto map(Board board) {
        BoardResponseDto dto = new BoardResponseDto();
        dto.id = board.getSeq();
        dto.title = board.getTitle();
        dto.content = board.getContent();
        dto.regId = board.getRegId();
        dto.createdDate = board.getCreatedDate();
        dto.updatedDate = board.getUpdatedDate();
        return dto;
    }
}
