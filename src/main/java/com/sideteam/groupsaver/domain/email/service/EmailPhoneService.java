package com.sideteam.groupsaver.domain.email.service;

import com.sideteam.groupsaver.domain.email.dto.request.FindEmailByPhoneRequest;
import com.sideteam.groupsaver.domain.email.dto.response.FindEmailResponse;
import com.sideteam.groupsaver.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class EmailPhoneService {
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public FindEmailResponse findEmail(FindEmailByPhoneRequest phoneRequest) {
        String email = memberRepository.findEmailByPhoneNumber(phoneRequest.getPhone());
        return FindEmailResponse.of(email, memberRepository.findCreateAtByEmail(email).toLocalDate());
    }
}
