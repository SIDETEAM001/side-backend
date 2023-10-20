package com.sideteam.groupsaver.domain.auth.service;

import com.sideteam.groupsaver.domain.auth.dto.request.LoginRequest;
import com.sideteam.groupsaver.domain.auth.dto.response.TokenDto;
import com.sideteam.groupsaver.domain.member.domain.Member;
import com.sideteam.groupsaver.domain.member.repository.MemberRepository;
import com.sideteam.groupsaver.global.auth.jwt.JwtTokenProvider;
import com.sideteam.groupsaver.global.auth.refresh_token.RefreshToken;
import com.sideteam.groupsaver.global.auth.refresh_token.RefreshTokenService;
import com.sideteam.groupsaver.global.auth.userdetails.CustomUserDetails;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
@Service
public class AuthTokenService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;

    private final MemberRepository memberRepository;


    public TokenDto login(final LoginRequest loginRequest) {

        final CustomUserDetails userDetails = setAuthentication(loginRequest);

        final long memberId = userDetails.getId();

        final String accessToken = jwtTokenProvider.issueJwtToken(memberId);
        final String refreshToken = refreshTokenService.issueRefreshToken(memberId);

        log.info("로그인, 토큰 신규 발급. Member ID: {}", userDetails.getId());

        return new TokenDto(accessToken, refreshToken);
    }

    public TokenDto generate(Long memberId) {

        final String accessToken = jwtTokenProvider.issueJwtToken(memberId);
        final String refreshToken = refreshTokenService.issueRefreshToken(memberId);

        log.info("로그인, 토큰 신규 발급. Member ID: {}", memberId);

        return new TokenDto(accessToken, refreshToken);
    }

    /**
     * 기존의 유효한 Refresh 토큰으로 새로운 Access 토큰과 Refresh 토큰을 발급하는 함수
     *
     * @param requestRefreshToken - 토큰 재발급 정보
     * @return - 재발급된 토클들 반환
     */
    public TokenDto refreshTokens(final String requestRefreshToken) {
        // Refresh 토큰 재발급
        final RefreshToken refreshToken = refreshTokenService.refresh(requestRefreshToken);

        final long memberId = refreshToken.getMemberId();

        // Access Token 재발급
        final String newAccessToken = jwtTokenProvider.issueJwtToken(memberId);

        log.info("JWT, 리프래시 토큰 재발급 완료. Member ID : {}", memberId);

        return new TokenDto(newAccessToken, refreshToken.getToken());
    }


    private CustomUserDetails setAuthentication(final LoginRequest loginRequest) {
        final long memberId = findMemberIdByEmail(loginRequest.email());

        final Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(memberId, loginRequest.password()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return (CustomUserDetails) authentication.getPrincipal();
    }

    private long findMemberIdByEmail(final String email) {
        final Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));

        return member.getId();
    }


}
