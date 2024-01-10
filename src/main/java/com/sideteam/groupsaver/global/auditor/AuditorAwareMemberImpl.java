package com.sideteam.groupsaver.global.auditor;

import com.sideteam.groupsaver.domain.member.domain.Member;
import com.sideteam.groupsaver.domain.member.repository.MemberRepository;
import com.sideteam.groupsaver.global.auth.userdetails.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class AuditorAwareMemberImpl implements AuditorAware<Member> {

    private final MemberRepository memberRepository;

    @Override
    public Optional<Member> getCurrentAuditor() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails userDetails) {
            final Long id = userDetails.getId();
            return Optional.of(memberRepository.getReferenceById(id));
        }
        return Optional.empty();
    }

}
