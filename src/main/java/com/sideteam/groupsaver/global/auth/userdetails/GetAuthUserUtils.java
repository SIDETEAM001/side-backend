package com.sideteam.groupsaver.global.auth.userdetails;

import com.sideteam.groupsaver.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@RequiredArgsConstructor
public class GetAuthUserUtils {
    private final MemberRepository repository;

    public static Long getAuthUserId() {
        return Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}