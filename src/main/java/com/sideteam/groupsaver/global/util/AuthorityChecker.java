package com.sideteam.groupsaver.global.util;

import com.sideteam.groupsaver.domain.member.domain.MemberRole;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.util.function.BooleanSupplier;

@Component
public class AuthorityChecker {

    @PreAuthorize("isAuthenticated() AND (( #memberId == principal.id ) OR hasRole(" + MemberRole.ROLES.ADMIN + "))")
    public boolean hasAuthority(Long memberId) {
        return true;
    }

    @PreAuthorize("isAuthenticated() AND ( ( #memberId == principal.id ) " +
            "AND #supplier.asBoolean " +
            "OR hasRole(" + MemberRole.ROLES.ADMIN + "))")
    public boolean hasAuthority(Long memberId, BooleanSupplier supplier) {
        return true;
    }

    @PreAuthorize("isAuthenticated()")
    @Secured(MemberRole.ROLES.ADMIN)
    public boolean isAdmin() {
        return true;
    }

}
