package com.sideteam.groupsaver.domain.mypage.dto.response;

import com.sideteam.groupsaver.domain.join.enums.DevelopCategory;
import com.sideteam.groupsaver.domain.join.enums.HobbyCategory;
import com.sideteam.groupsaver.domain.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyInfoFindResponse {

    private Long id;
    private String password;
    private String phoneNumber;
    private String nickname;
    private String email;
    private List<DevelopCategory> developCategoryList;
    private List<HobbyCategory> hobbyCategoryList;

    @Builder
    public static MyInfoFindResponse toDto(Member member) {
        return MyInfoFindResponse.builder()
                .id(member.getId())
                .password(member.getPassword())
                .phoneNumber(member.getPhoneNumber())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .build();
    }
}
