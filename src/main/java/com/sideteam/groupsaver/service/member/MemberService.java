package com.sideteam.groupsaver.service.member;


import com.sideteam.groupsaver.config.security.CustomAuthorityUtils;
import com.sideteam.groupsaver.persistence.domain.member.Member;
import com.sideteam.groupsaver.persistence.repository.member.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomAuthorityUtils customAuthorityUtils;

    public String saveMember(Requirement requirement) {
        String encryptedPassword = passwordEncoder.encode(requirement.pw);
        List<String> roles = customAuthorityUtils.createRole(requirement.email);
        Member member = requirement.toEntity(encryptedPassword, roles);
        memberRepository.save(member);
        return member.getName();
    }

    public record Requirement(String email, String pw, String name){
        public Member toEntity(String encryptedPw, List<String> roles) {
            return Member.of( email, encryptedPw, name, roles);
        }
    }

}