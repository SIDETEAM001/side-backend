package com.sideteam.groupsaver.domain.mypage.dto.response;

import com.sideteam.groupsaver.domain.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
