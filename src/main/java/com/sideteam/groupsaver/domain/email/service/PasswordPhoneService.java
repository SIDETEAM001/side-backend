package com.sideteam.groupsaver.domain.email.service;

import com.sideteam.groupsaver.domain.email.dto.request.ChangePwPhoneRequest;
import com.sideteam.groupsaver.domain.member.domain.Member;
import com.sideteam.groupsaver.domain.member.repository.MemberRepository;
import com.sideteam.groupsaver.global.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.sideteam.groupsaver.global.exception.member.MemberErrorCode.MEMBER_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class PasswordPhoneService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void changePassword(ChangePwPhoneRequest phoneRequest) {
        Member member = memberRepository.findByPhoneNumber(phoneRequest.getPhone()).orElseThrow(() ->
                new BusinessException(MEMBER_NOT_FOUND, "존재하지 않는 전화번호 : " + phoneRequest.getPhone()));
        member.updatePassword(passwordEncoder.encode(phoneRequest.getPassword()));
    }
}
