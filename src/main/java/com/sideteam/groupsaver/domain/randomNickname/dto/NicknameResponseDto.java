package com.sideteam.groupsaver.domain.randomNickname.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NicknameResponseDto {
    private String nickName;

    @Builder
    private NicknameResponseDto(String nickName) {
        this.nickName = nickName;
    }

    public static NicknameResponseDto of(String nickName) {
        return NicknameResponseDto.builder()
                .nickName(nickName)
                .build();
    }
}
