package com.sideteam.groupsaver.domain.auth.controller;

import com.sideteam.groupsaver.domain.auth.dto.request.LoginRequest;
import com.sideteam.groupsaver.domain.auth.dto.request.SignupRequest;
import com.sideteam.groupsaver.domain.auth.dto.request.TokenRefreshRequest;
import com.sideteam.groupsaver.domain.auth.dto.response.SignupResult;
import com.sideteam.groupsaver.domain.auth.dto.response.TokenDto;
import com.sideteam.groupsaver.domain.auth.service.AuthSignupService;
import com.sideteam.groupsaver.domain.auth.service.AuthTokenService;
import com.sideteam.groupsaver.domain.auth.service.OAuthLoginService;
import com.sideteam.groupsaver.global.auth.oauth.kakao.KakaoLoginParams;

import com.sideteam.groupsaver.global.util.CookieUtils;
import jakarta.servlet.http.HttpServletRequest;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthSignupService authSignupService;
    private final AuthTokenService authTokenService;
    private final OAuthLoginService oAuthLoginService;


    @PostMapping("/signup")
    public ResponseEntity<SignupResult> signup(@Valid @RequestBody SignupRequest signupRequest) {
        return new ResponseEntity<>(authSignupService.signup(signupRequest), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authTokenService.login(loginRequest));
    }

    @PostMapping("/token")
    public ResponseEntity<TokenDto> refreshToken(
            @Valid @RequestBody TokenRefreshRequest refreshTokenRequest
    ) {
        return ResponseEntity.ok(authTokenService.refreshTokens(refreshTokenRequest.refreshToken()));
    }

    @PostMapping("/kakao")
    public ResponseEntity<TokenDto> loginKakao(@RequestBody KakaoLoginParams params) {
        return ResponseEntity.ok(oAuthLoginService.login(params));
    }

    @DeleteMapping("/leave")
    public ResponseEntity<Void> leave(HttpServletRequest request,
                                      @Valid @RequestBody TokenRefreshRequest refreshTokenRequest) {
//        authTokenService.deleteMember(request, refreshToken);
        return ResponseEntity.ok().build();
    }

}
