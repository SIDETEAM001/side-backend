package com.sideteam.groupsaver.domain.likes.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class LikesResponseDto {

    private Long id;
    private String body;
    private String nickName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private Instant createAt;

}
