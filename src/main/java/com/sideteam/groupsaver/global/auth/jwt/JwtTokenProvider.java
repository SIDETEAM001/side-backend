package com.sideteam.groupsaver.global.auth.jwt;

import com.sideteam.groupsaver.global.auth.AuthConstants;
import com.sideteam.groupsaver.global.exception.ErrorCodeUtils;
import com.sideteam.groupsaver.global.exception.auth.AuthErrorCode;
import com.sideteam.groupsaver.global.exception.auth.AuthErrorException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Slf4j
@Component
public final class JwtTokenProvider {

    private final Key tokenSecretKey;
    private final Duration tokenExpiration;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;


    public JwtTokenProvider(
            @Value("${app.token.access.expiration}") Duration accessTime,
            @Value("${app.token.access.secret-key}") String secretKey) {
        this.tokenExpiration = accessTime;
        final byte[] keyBytes = Base64.getEncoder().encode(secretKey.getBytes()); // 비공개 키를 Base64 인코딩
        this.tokenSecretKey = Keys.hmacShaKeyFor(keyBytes);
    }


    public static String parseJwtFromRequest(HttpServletRequest request) {
        final String headerAuth = request.getHeader(HttpHeaders.AUTHORIZATION);
        return parseJwt(headerAuth);
    }

    /**
     * 헤더로부터 JWT 토큰을 추출해서 반환
     *
     * @param headerAuth - 헤더 문자열
     * @return - 추출한 JWT 토큰
     */
    public static String parseJwt(final String headerAuth) {

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(AuthConstants.TOKEN_TYPE)) {
            return headerAuth.substring(AuthConstants.TOKEN_TYPE.length()).trim();
        } else {
            return "";
        }
    }

    /**
     * 새로운 JWT 토큰을 발행
     *
     * @param memberId - JWT 토큰 구분 대상
     * @return - 새로 발급한 JWT 토큰
     */
    public String issueJwtToken(final String memberId) {

        final Instant now = Instant.now();
        final Instant expiryDate = createExpiryDate(now);

        return Jwts.builder()
                .setHeader(createHeader())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiryDate))
                .setClaims(createClaims(memberId, expiryDate))
                .signWith(tokenSecretKey, signatureAlgorithm)
                .compact();
    }

    /**
     * 이상이 없는 토큰인 경우, 해당 토큰 소유 대상 정보를 반환
     *
     * @param accessToken - JWT 인증 토큰
     * @return - 토큰 소유 대상
     */
    public String getSubject(final String accessToken) {
        return verifyAndGetClaims(accessToken).getSubject();
    }

    public Instant getExpiryDate(final String accessToken) {
        return verifyAndGetClaims(accessToken).getExpiration().toInstant();
    }

    public Claims verifyAndGetClaims(final String accessToken) throws AuthErrorException {
        try {
            return getClaimsFormToken(accessToken, tokenSecretKey);
        } catch (JwtException jwtException) {
            AuthErrorCode errorCode = ErrorCodeUtils.mappingAuthErrorCodeOrDefault(jwtException, AuthErrorCode.FAILED_AUTHENTICATION);

            log.warn("Error code : {}, Error - {},  {}", errorCode, jwtException.getClass(), jwtException.getMessage());
            throw new AuthErrorException(errorCode, jwtException.getMessage());
        }
    }

    private Map<String, Object> createHeader() {
        return Map.of(
                Header.TYPE, Header.JWT_TYPE
        );
    }

    private static Map<String, Object> createClaims(final String subject, final Instant expiryDate) {
        return Map.of(
                Claims.SUBJECT, subject,
                Claims.EXPIRATION, Date.from(expiryDate)
        );
    }

    private Instant createExpiryDate(final Instant since) {
        return since.plus(tokenExpiration);
    }

    private static Claims getClaimsFormToken(final String token, final Key secretKey) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
