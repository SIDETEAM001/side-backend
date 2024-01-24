package com.sideteam.groupsaver.global.emailAuthCode;

import com.sideteam.groupsaver.global.auth.refresh_token.RefreshToken;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@RedisHash(value = "auth_code")
@Getter
public class AuthCode {
    @Id
    private final String email;

    private final String code;

    @TimeToLive
    private final long timeToLive;

    @Builder
    protected AuthCode(String email, String code, long timeToLive) {
        this.email = email;
        this.code = code;
        this.timeToLive = timeToLive;
    }

    public static AuthCode of(String email, String code, long time) {
        return AuthCode.builder()
                .email(email)
                .code(code)
                .timeToLive(time)
                .build();
    }
}
