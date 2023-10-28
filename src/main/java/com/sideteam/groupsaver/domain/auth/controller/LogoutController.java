package com.sideteam.groupsaver.domain.auth.controller;

import com.sideteam.groupsaver.domain.auth.service.AuthLogoutService;
import com.sideteam.groupsaver.global.auth.AuthConstants;
import com.sideteam.groupsaver.global.resolver.access_token.AccessToken;
import com.sideteam.groupsaver.global.resolver.access_token.AccessTokenInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/accounts")
public class LogoutController {

    private final AuthLogoutService authLogoutService;


    @PostMapping("/logout")
    public ResponseEntity<?> logout(
            @CookieValue(AuthConstants.REFRESH_TOKEN) String refreshToken,
            @AccessTokenInfo AccessToken accessToken
    ) {
        authLogoutService.logout(accessToken, refreshToken);
        return ResponseEntity.noContent().build();
    }

}
