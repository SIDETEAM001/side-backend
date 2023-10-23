package com.sideteam.groupsaver.domain.auth.service;

import com.sideteam.groupsaver.domain.auth.dto.request.SignupRequest;
import com.sideteam.groupsaver.domain.auth.dto.response.SignupResult;
import com.sideteam.groupsaver.domain.member.domain.Member;
import com.sideteam.groupsaver.domain.member.domain.MemberRole;
import com.sideteam.groupsaver.domain.member.repository.MemberRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@RequiredArgsConstructor
@Slf4j
@Service
public class AuthSignupService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public SignupResult signup(final SignupRequest signupRequest) {

        checkDuplication(signupRequest);

        final var roles = Set.of(MemberRole.USER);

        final var member = Member.builder()
                .email(signupRequest.email())
                .nickname(signupRequest.nickname())
                .roles(roles)
                .password(encodePassword(signupRequest))
                .phoneNumber(signupRequest.phoneNumber())
                .build();
        memberRepository.save(member);

        log.info("회원가입 완료: ID: {}, 이메일: {}, 닉네임: {}", member.getId(), member.getEmail(), member.getNickname());

        return new SignupResult();
    }


    private void checkDuplication(final SignupRequest signupRequest) {
        final boolean isDuplicated = memberRepository.existsByEmail(signupRequest.email());

        if (isDuplicated) {
            log.error("중복되는 이메일: {}", signupRequest.email());
//            throw new
        }
    }

    private String encodePassword(final SignupRequest signupRequest) {
        return passwordEncoder.encode(signupRequest.password());
    }

}
