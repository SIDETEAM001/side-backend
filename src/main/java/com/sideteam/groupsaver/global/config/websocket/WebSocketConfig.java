package com.sideteam.groupsaver.global.config.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {
    private final ClubChatSocketHandler clubChatSocketHandler;
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(clubChatSocketHandler, "/ws/club/chat")
                .setAllowedOriginPatterns("*");

    }
}