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

    private final long memberId;

    @TimeToLive
    private long timeToLive;

    @Builder
    protected RefreshToken(String token, long memberId, long timeToLive) {
        this.token = token;
        this.memberId = memberId;
        this.timeToLive = timeToLive;
    }

    public void refresh(String newToken, long newTimeToLive) {
        this.token = newToken;
        this.timeToLive = newTimeToLive;
    }

}
