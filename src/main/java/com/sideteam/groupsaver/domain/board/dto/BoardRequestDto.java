package com.sideteam.groupsaver.domain.board.dto;

import com.sideteam.groupsaver.domain.board.domain.Board;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardRequestDto {
    private String title;
    private String content;
    private String regId;

    public Board toEntity() {
        return Board.builder()
                .title(title)
                .content(content)
                .redId(regId)
                .build();
    }
}
