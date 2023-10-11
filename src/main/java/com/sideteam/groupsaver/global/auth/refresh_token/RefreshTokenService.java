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
     * @param id - 리프래시 토큰 구분 대상
     * @return - 리프래시 토큰
     */
    public String issueRefreshToken(final long id) {
        final RefreshToken refreshToken = RefreshToken.builder()
                .token(makeRefreshToken())
                .memberId(id)
                .timeToLive(getExpiryTime())
                .build();

        refreshTokenRepository.save(refreshToken);

        log.info("리프래시 토큰 신규 발급. Member ID: {}", id);
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
     * @param currentMemberId   - 현재 인증된 멤버 ID
     * @param savedRefreshToken - 기존의 저장된 토큰
     */
    public void logout(final long currentMemberId, final String savedRefreshToken) {

        final RefreshToken refreshToken = findOrThrow(savedRefreshToken);
        final long expectedMemberId = refreshToken.getMemberId();

        if (expectedMemberId != currentMemberId) {
            final String errorMessage = String.format("비정상적인 로그아웃 시도! 현재 멤버 ID: %s, But tried ID: %s", currentMemberId, expectedMemberId);
            log.warn(errorMessage);
            throw new AuthErrorException(AuthErrorCode.MISMATCHED_REFRESH_TOKEN, errorMessage);
        }

        refreshTokenRepository.delete(refreshToken);
        log.info("로그아웃: ID: {}", currentMemberId);
    }

    private String makeRefreshToken() {
        return UUID.randomUUID().toString();
    }

    private long getExpiryTime() {
        return refreshTokenExpiration.toSeconds();
    }

    private RefreshToken findOrThrow(final String refreshToken) {
        return refreshTokenRepository.findById(refreshToken)
                .orElseThrow(() -> new AuthErrorException(AuthErrorCode.REFRESH_TOKEN_NOT_FOUND, "인증 정보를 찾을 수 없습니다"));
    }

}
