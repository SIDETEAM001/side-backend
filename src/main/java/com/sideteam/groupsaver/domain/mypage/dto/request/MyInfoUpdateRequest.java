package com.sideteam.groupsaver.domain.mypage.dto.request;

import com.sideteam.groupsaver.domain.join.enums.DevelopCategory;
import com.sideteam.groupsaver.domain.join.enums.HobbyCategory;
import com.sideteam.groupsaver.domain.join.enums.JobCategory;
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
    private JobCategory jobCategory;
    private List<HobbyCategory> hobbyCategory;
    private List<DevelopCategory> developCategory;
}
