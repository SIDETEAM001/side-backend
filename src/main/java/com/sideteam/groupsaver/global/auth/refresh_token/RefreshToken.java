package com.sideteam.groupsaver.global.auth.refresh_token;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@RedisHash(value = "refresh_token")
@Getter
public class RefreshToken {
    @Id
    private String token;

    private final String memberId;

    @TimeToLive
    private long timeToLive;

    @Builder
    protected RefreshToken(String token, String memberId, long timeToLive) {
        this.token = token;
        this.memberId = memberId;
        this.timeToLive = timeToLive;
    }

    public static RefreshToken of(final RefreshToken refreshToken, final String newToken, long newTtl) {
        return RefreshToken.builder()
                .token(newToken)
                .memberId(refreshToken.memberId)
                .timeToLive(newTtl)
                .build();
    }

}
