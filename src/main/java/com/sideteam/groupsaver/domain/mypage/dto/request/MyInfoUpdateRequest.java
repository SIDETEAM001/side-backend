package com.sideteam.groupsaver.domain.mypage.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sideteam.groupsaver.domain.category.domain.ClubCategoryMajor;
import com.sideteam.groupsaver.domain.category.domain.JobMajor;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import static com.sideteam.groupsaver.global.util.ProjectTimeFormat.LOCAL_DATE_PATTERN;
import static com.sideteam.groupsaver.global.util.ProjectTimeFormat.LOCAL_DATE_PATTERN_EXAMPLE;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class MyInfoUpdateRequest {
    private String nickname;
    @Schema(description = "생년월일", example = LOCAL_DATE_PATTERN_EXAMPLE, type = "string")
    @JsonFormat(pattern = LOCAL_DATE_PATTERN)
    private LocalDate birth;
    private String url;
    private JobMajor jobCategory;
    private List<ClubCategoryMajor> clubCategories;
}
