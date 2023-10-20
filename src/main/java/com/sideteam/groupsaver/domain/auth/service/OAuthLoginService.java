package com.sideteam.groupsaver.domain.auth.service;

import com.sideteam.groupsaver.domain.auth.dto.response.TokenDto;
import com.sideteam.groupsaver.domain.member.domain.MemberRole;
import com.sideteam.groupsaver.domain.member.domain.OAuthMember;
import com.sideteam.groupsaver.domain.member.repository.OAuthMemberRepository;
import com.sideteam.groupsaver.global.auth.oauth.OAuthInfoResponse;
import com.sideteam.groupsaver.global.auth.oauth.OAuthLoginParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class OAuthLoginService {
    private final OAuthMemberRepository oAuthMemberRepository;
    private final AuthTokenService authTokenService;
    private final RequestOAuthInfoService requestOAuthInfoService;

    public TokenDto login(OAuthLoginParams params) {
        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
        Long memberId = findOrCreateMember(oAuthInfoResponse);
        return authTokenService.generate(memberId);
    }

    private Long findOrCreateMember(OAuthInfoResponse oAuthInfoResponse) {
        return oAuthMemberRepository.findByEmail(oAuthInfoResponse.getEmail())
                .map(OAuthMember::getId)
                .orElseGet(() -> newMember(oAuthInfoResponse));
    }

    private Long newMember(OAuthInfoResponse oAuthInfoResponse) {

        return oAuthMemberRepository.save(OAuthMember.builder()
                .email(oAuthInfoResponse.getEmail())
                .nickname(oAuthInfoResponse.getNickname())
                .oAuthProvider(oAuthInfoResponse.getOAuthProvider())
                .build()).getId();
    }
}
