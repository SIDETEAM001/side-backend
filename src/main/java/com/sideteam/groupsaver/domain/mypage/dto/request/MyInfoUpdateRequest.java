package com.sideteam.groupsaver.domain.mypage.dto.request;

import com.sideteam.groupsaver.domain.join.enums.DevelopCategory;
import com.sideteam.groupsaver.domain.join.enums.HobbyCategory;
import com.sideteam.groupsaver.domain.join.enums.JobCategory;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyInfoUpdateRequest {
    private String nickname;
    @Pattern(regexp = "^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$", message = "연월일 형식(yyyy-MM-dd)에 맞지 않습니다")
    private String birth;
    private String url;
    private JobCategory jobCategory;
    private List<HobbyCategory> hobbyCategory;
    private List<DevelopCategory> developCategory;
}
