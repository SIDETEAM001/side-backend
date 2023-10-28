package com.sideteam.groupsaver.domain.auth.service;

import com.sideteam.groupsaver.global.auth.refresh_token.RefreshToken;
import com.sideteam.groupsaver.global.auth.refresh_token.RefreshTokenService;
import com.sideteam.groupsaver.global.exception.auth.AuthErrorCode;
import com.sideteam.groupsaver.global.exception.auth.AuthErrorException;
import com.sideteam.groupsaver.global.resolver.access_token.AccessToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthLogoutService {

    private final RefreshTokenService refreshTokenService;

    public void logout(final AccessToken accessToken, final String logoutRefreshToken) {

        final RefreshToken refreshToken = refreshTokenService.findOrThrow(logoutRefreshToken);
        final String currentSubject = accessToken.subject();
        final String tokenSubject = refreshToken.getMemberId();

        checkTokenOwner(currentSubject, tokenSubject);

        refreshTokenService.deleteToken(refreshToken);
        log.info("로그아웃: {}", currentSubject);
    }


    private void checkTokenOwner(final String currentSubject, final String tokenSubject) {
        if (!currentSubject.equals(tokenSubject)) {
            final var errorMessage = String.format("비정상적인 로그아웃 시도! 현재 멤버 ID: %s, But tried ID: %s", currentSubject, tokenSubject);
            log.warn(errorMessage);
            throw new AuthErrorException(AuthErrorCode.MISMATCHED_REFRESH_TOKEN, errorMessage);
        }
    }

}
