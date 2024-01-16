package com.sideteam.groupsaver.domain.mypage.dto.response;

import com.sideteam.groupsaver.domain.category.domain.ClubCategoryMajor;
import com.sideteam.groupsaver.domain.member.domain.Member;
import lombok.*;

import java.util.List;

@Getter
@Builder
public class MyInfoFindResponse {

    private Long id;
    private String phoneNumber;
    private String nickname;
    private String email;
    private List<ClubCategoryMajor> clubCategories;

    @Builder
    public static MyInfoFindResponse toDto(Member member) {
        return MyInfoFindResponse.builder()
                .id(member.getId())
                .phoneNumber(member.getPhoneNumber())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .build();
    }
}
