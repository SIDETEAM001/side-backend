package com.sideteam.groupsaver.domain.auth.controller;

import com.sideteam.groupsaver.domain.auth.dto.request.LoginRequest;
import com.sideteam.groupsaver.domain.auth.dto.request.SignupRequest;
import com.sideteam.groupsaver.domain.auth.dto.response.TokenDto;
import com.sideteam.groupsaver.domain.auth.service.AuthSignupService;
import com.sideteam.groupsaver.domain.auth.service.AuthTokenService;
import com.sideteam.groupsaver.domain.auth.service.OAuthLoginService;
import com.sideteam.groupsaver.global.auth.AuthConstants;
import com.sideteam.groupsaver.global.auth.oauth.kakao.KakaoLoginParams;
import com.sideteam.groupsaver.global.util.CookieUtils;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthSignupService authSignupService;
    private final AuthTokenService authTokenService;
    private final OAuthLoginService oAuthLoginService;


    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest signupRequest) {
        final var result = authSignupService.signup(signupRequest);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        final var result = authTokenService.login(loginRequest);
        return ResponseEntity.noContent()
                .headers(createLoginTokenHeaders(result))
                .build();
    }

    @PostMapping("/token")
    public ResponseEntity<?> refreshToken(
            @CookieValue(AuthConstants.REFRESH_TOKEN) String refreshToken
    ) {
        final var result = authTokenService.refreshTokens(refreshToken);
        return ResponseEntity.noContent()
                .headers(createLoginTokenHeaders(result))
                .build();
    }


    @PostMapping("/kakao")
    public ResponseEntity<TokenDto> loginKakao(@RequestBody KakaoLoginParams params) {
        return ResponseEntity.ok(oAuthLoginService.login(params));
    }


    private HttpHeaders createLoginTokenHeaders(final TokenDto tokenDto) {

        final HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, getAccessToken(tokenDto));
        headers.set(HttpHeaders.SET_COOKIE, createRefreshTokenCookie(tokenDto));

        return headers;
    }

    private String getAccessToken(final TokenDto tokenDto) {
        return tokenDto.type() + tokenDto.accessToken();
    }

    private String createRefreshTokenCookie(final TokenDto tokenDto) {
        return CookieUtils.createCookie(
                AuthConstants.REFRESH_TOKEN,
                tokenDto.refreshToken(),
                Duration.ofDays(7)
        ).toString();
    }

}
