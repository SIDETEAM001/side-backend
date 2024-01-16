package com.sideteam.groupsaver.domain.mypage.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sideteam.groupsaver.domain.category.domain.ClubCategoryMajor;
import com.sideteam.groupsaver.domain.category.domain.JobMajor;
import com.sideteam.groupsaver.global.util.ProjectTimeFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyInfoUpdateRequest {
    private String nickname;
    @JsonFormat(pattern = ProjectTimeFormat.LOCAL_DATE_PATTERN)
    private LocalDate birth;
    //    @Pattern(regexp = "^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$", message = "연월일 형식(yyyy-MM-dd)에 맞지 않습니다")
    //    private String birth;
    private String url;
    private JobMajor jobCategory;
    private List<ClubCategoryMajor> clubCategories;
}
