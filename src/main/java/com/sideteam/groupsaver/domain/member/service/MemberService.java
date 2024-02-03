package com.sideteam.groupsaver.domain.member.service;

import com.sideteam.groupsaver.domain.member.domain.Member;
import com.sideteam.groupsaver.domain.member.repository.MemberRepository;
import com.sideteam.groupsaver.global.auth.userdetails.GetAuthUserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member findMember() {
        long memberId = GetAuthUserUtils.getAuthUserId();
        return memberRepository.findById(memberId).orElseThrow(IllegalArgumentException::new);
    }

    // todo
    public List<Member> findMemberListByCategory() {
        return null;
    }
}