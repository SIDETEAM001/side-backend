package com.sideteam.groupsaver.domain.member.service;

import com.sideteam.groupsaver.domain.member.domain.Member;
import com.sideteam.groupsaver.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberUpdateService {

    private final MemberRepository memberRepository;

    @PreAuthorize("@authorityChecker.isAdmin()")
    public void suspendMember(Long memberId) {
        Member member = memberRepository.findByIdOrThrow(memberId);
        member.suspend();
    }

    @PreAuthorize("@authorityChecker.hasAuthority(#memberId)")
    public void withdrawMember(Long memberId) {
        memberRepository.deleteById(memberId);
    }

}
