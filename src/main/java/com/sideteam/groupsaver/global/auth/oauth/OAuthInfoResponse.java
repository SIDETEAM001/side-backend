package com.sideteam.groupsaver.global.auth.oauth;

import com.sideteam.groupsaver.domain.member.domain.OAuthProvider;

public interface OAuthInfoResponse {
    String getEmail();
    String getNickname();
    OAuthProvider getOAuthProvider();
}
