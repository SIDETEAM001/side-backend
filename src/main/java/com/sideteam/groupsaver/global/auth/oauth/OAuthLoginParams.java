package com.sideteam.groupsaver.global.auth.oauth;

import com.sideteam.groupsaver.domain.member.domain.OAuthProvider;
import org.springframework.util.MultiValueMap;

public interface OAuthLoginParams {
    OAuthProvider oAuthProvider();
    MultiValueMap<String, String> makeBody();
}