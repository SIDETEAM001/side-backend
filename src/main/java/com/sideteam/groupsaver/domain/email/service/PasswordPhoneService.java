package com.sideteam.groupsaver.domain.email.service;

import com.sideteam.groupsaver.domain.email.dto.request.ChangePwPhoneRequest;
import com.sideteam.groupsaver.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordPhoneService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void changePassword(ChangePwPhoneRequest phoneRequest) {
        memberRepository.updatePasswordByPhoneNumber(phoneRequest.getPassword(), passwordEncoder.encode(phoneRequest.getPassword()));
    }
}
