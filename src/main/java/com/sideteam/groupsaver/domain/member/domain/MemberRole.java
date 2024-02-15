package com.sideteam.groupsaver.domain.member.domain;

import com.sideteam.groupsaver.global.util.converter.EnumValue;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum MemberRole implements EnumValue {

    USER("USER", ROLES.USER, "회원"), // 등록된 사용자
    ADMIN("ADMIN", ROLES.ADMIN, "관리자"), // 관리자

    ;

    private final String code;
    private final String key;
    private final String title;

    public static Collection<? extends GrantedAuthority> convertToAuthorities(MemberRole roles) {
        return List.of(new SimpleGrantedAuthority(roles.getKey()));
    }

    @Override
    public String getName() {
        return title;
    }


    public static class ROLES {
        public static final String USER = "ROLE_USER";
        public static final String ADMIN = "ROLE_ADMIN";

        private ROLES() {
        }

        /**
         * 권한이 낮은 순위순으로 반환
         *
         * @return - 권한 목록
         */
        public static List<String> rank() {
            return List.of(USER, ADMIN);
        }
    }

}
