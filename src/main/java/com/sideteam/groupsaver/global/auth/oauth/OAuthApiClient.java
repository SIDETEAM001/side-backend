package com.sideteam.groupsaver.global.auth.oauth;

import com.sideteam.groupsaver.domain.member.domain.OAuthProvider;

public interface OAuthApiClient {
    OAuthProvider oAuthProvider();
    String requestAccessToken(OAuthLoginParams params);
    OAuthInfoResponse requestOauthInfo(String accessToken);
}
