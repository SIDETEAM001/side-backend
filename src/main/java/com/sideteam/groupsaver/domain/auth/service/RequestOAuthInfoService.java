package com.sideteam.groupsaver.domain.auth.service;

import com.sideteam.groupsaver.domain.member.domain.OAuthProvider;
import com.sideteam.groupsaver.global.auth.oauth.OAuthApiClient;
import com.sideteam.groupsaver.global.auth.oauth.OAuthInfoResponse;
import com.sideteam.groupsaver.global.auth.oauth.OAuthLoginParams;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class RequestOAuthInfoService {
    private final Map<OAuthProvider, OAuthApiClient> clients;

    public RequestOAuthInfoService(List<OAuthApiClient> clients) {
        this.clients = clients.stream().collect(
                Collectors.toUnmodifiableMap(OAuthApiClient::oAuthProvider, Function.identity())
        );
    }

    public OAuthInfoResponse request(OAuthLoginParams params) {
        OAuthApiClient client = clients.get(params.oAuthProvider());
        String accessToken = client.requestAccessToken(params);
        return client.requestOauthInfo(accessToken);
    }
}