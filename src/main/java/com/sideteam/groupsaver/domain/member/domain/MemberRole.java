package com.sideteam.groupsaver.domain.member.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum MemberRole {

    USER("1", "ROLE_USER", "회원"), // 등록된 사용자
    ADMIN("9", "ROLE_ADMIN", "관리자") // 관리자

    ;

    private final String code;
    private final String key;
    private final String title;


    public static Collection<? extends GrantedAuthority> convertToAuthorities(Collection<MemberRole> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getKey()))
                .collect(Collectors.toSet());
    }

}
