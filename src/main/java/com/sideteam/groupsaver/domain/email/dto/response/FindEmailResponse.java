package com.sideteam.groupsaver.domain.email.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static com.sideteam.groupsaver.global.util.ProjectTimeFormat.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FindEmailResponse {
    private String email;

    @Schema(description = "생성일", example = LOCAL_CREATE_DATE_PATTERN_EXAMPLE, type = "string")
    @JsonFormat(pattern = LOCAL_CREATE_DATE_PATTERN)
    private LocalDate createAt;

    @Builder
    protected FindEmailResponse(String email, LocalDate createAt) {
        this.email = email;
        this.createAt = createAt;
    }

    public static FindEmailResponse of(String email, LocalDate createAt) {
        return FindEmailResponse.builder()
                .email(email)
                .createAt(createAt)
                .build();
    }
}
