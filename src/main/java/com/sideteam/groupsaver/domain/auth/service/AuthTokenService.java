package com.sideteam.groupsaver.domain.auth.service;

import com.sideteam.groupsaver.domain.auth.dto.request.LoginRequest;
import com.sideteam.groupsaver.domain.auth.dto.response.TokenDto;
import com.sideteam.groupsaver.domain.member.domain.Member;
import com.sideteam.groupsaver.domain.member.repository.MemberRepository;
import com.sideteam.groupsaver.global.auth.TokenService;
import com.sideteam.groupsaver.global.auth.userdetails.CustomUserDetails;
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
    private final TokenService tokenService;

    private final MemberRepository memberRepository;


    /**
     * 로그인 요청이 오면 이메일과 비밀번호로 확인 후, 인증에 성공한 경우 토큰 DTO를 반환하는 함수
     *
     * @param loginRequest - 로그인 처리를 위한 정보
     * @return - 토큰 DTO
     */
    public TokenDto login(final LoginRequest loginRequest) {

        final CustomUserDetails userDetails = setAuthentication(loginRequest);

        final String memberId = userDetails.getId().toString();

        return tokenService.generate(memberId);
    }


    /**
     * 기존의 유효한 Refresh 토큰으로 새로운 Access 토큰과 Refresh 토큰을 발급하는 함수
     *
     * @param requestRefreshToken - 토큰 재발급 정보
     * @return - 재발급된 토클들 반환
     */
    public TokenDto refreshTokens(final String requestRefreshToken) {
        return tokenService.refreshTokens(requestRefreshToken);
    }


    private CustomUserDetails setAuthentication(final LoginRequest loginRequest) {
        final Long memberId = findMemberIdByEmail(loginRequest.email());

        final Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(memberId, loginRequest.password()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return (CustomUserDetails) authentication.getPrincipal();
    }

    private Long findMemberIdByEmail(final String email) {
        final Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));

        return member.getId();
    }


}
