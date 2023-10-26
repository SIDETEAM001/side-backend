package com.sideteam.groupsaver.domain.auth.service;

import com.sideteam.groupsaver.domain.auth.dto.response.TokenDto;
import com.sideteam.groupsaver.domain.member.domain.Member;
import com.sideteam.groupsaver.domain.member.domain.MemberRole;
import com.sideteam.groupsaver.domain.member.repository.MemberRepository;
import com.sideteam.groupsaver.global.auth.TokenService;
import com.sideteam.groupsaver.global.auth.oauth.OAuthInfoResponse;
import com.sideteam.groupsaver.global.auth.oauth.OAuthLoginParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class OAuthLoginService {
    private final MemberRepository memberRepository;
    private final TokenService tokenService;
    private final RequestOAuthInfoService requestOAuthInfoService;

    public TokenDto login(OAuthLoginParams params) {
        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
        Long memberId = findOrCreateMember(oAuthInfoResponse);
        return tokenService.generate(memberId.toString());
    }

    private Long findOrCreateMember(OAuthInfoResponse oAuthInfoResponse) {
        return memberRepository.findByEmail(oAuthInfoResponse.getEmail())
                .map(Member::getId)
                .orElseGet(() -> newMember(oAuthInfoResponse));
    }

    private Long newMember(OAuthInfoResponse oAuthInfoResponse) {
        final var roles = Set.of(MemberRole.USER);
        return memberRepository.save(Member.builder()
                .email(oAuthInfoResponse.getEmail())
                .nickname(oAuthInfoResponse.getNickname())
                .roles(roles)
                .oAuthProvider(oAuthInfoResponse.getOAuthProvider())
                .build()).getId();
    }
}
