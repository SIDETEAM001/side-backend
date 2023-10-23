package com.sideteam.groupsaver.global.auth.refresh_token;

import com.sideteam.groupsaver.global.exception.auth.AuthErrorCode;
import com.sideteam.groupsaver.global.exception.auth.AuthErrorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;

@Slf4j
@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final Duration refreshTokenExpiration;


    public RefreshTokenService(
            @Value("${app.token.refresh.expiration}") Duration refreshTokenExpiration,
            RefreshTokenRepository refreshTokenRepository
    ) {
        this.refreshTokenExpiration = refreshTokenExpiration;
        this.refreshTokenRepository = refreshTokenRepository;
    }


    /**
     * 리프래시 토큰을 새로 발행
     *
     * @param memberId - 리프래시 토큰 구분 대상
     * @return - 리프래시 토큰
     */
    public String issueRefreshToken(final String memberId) {
        final RefreshToken refreshToken = RefreshToken.builder()
                .token(makeRefreshToken())
                .memberId(memberId)
                .timeToLive(getExpiryTime())
                .build();

        refreshTokenRepository.save(refreshToken);

        log.info("리프래시 토큰 신규 발급. Member: {}", memberId);
        return refreshToken.getToken();
    }

    /**
     * 기존의 Refresh 토큰으로 새로운 Refresh 토큰을 발급하는 함수
     *
     * @param requestRefreshToken - 기존의 리프래시 토큰
     * @return - 새로운 리프래시 토큰
     */
    public RefreshToken refresh(final String requestRefreshToken) {
        final RefreshToken refreshToken = findOrThrow(requestRefreshToken);
        refreshToken.refresh(makeRefreshToken(), getExpiryTime());
        return refreshToken;
    }

    /**
     * 로그아웃하고 기존의 refresh 토큰은 폐기함(재사용 불가)
     *
     * @param refreshToken - 삭제할 refresh 토큰
     */
    public void deleteToken(final RefreshToken refreshToken) {
        final String logoutId = refreshToken.getMemberId();
        refreshTokenRepository.delete(refreshToken);
        log.info("로그아웃: ID: {}", logoutId);
    }

    public RefreshToken findOrThrow(final String refreshToken) {
        return refreshTokenRepository.findById(refreshToken)
                .orElseThrow(() -> new AuthErrorException(AuthErrorCode.REFRESH_TOKEN_NOT_FOUND, "인증 정보를 찾을 수 없습니다"));
    }


    private String makeRefreshToken() {
        return UUID.randomUUID().toString();
    }

    private long getExpiryTime() {
        return refreshTokenExpiration.toSeconds();
    }


}
