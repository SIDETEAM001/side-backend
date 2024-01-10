package com.sideteam.groupsaver.global.config.websocket;

import com.sideteam.groupsaver.domain.chat.domain.ChatMember;
import com.sideteam.groupsaver.domain.chat.dto.ChatMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ClubChatMainHandler extends TextWebSocketHandler {
    private static final HashMap<String, List<WebSocketSession>> CLIENTS = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    }


    private void sendMessageToChatRoom(ChatMessageDto chatMessageDto, List<WebSocketSession> chatRoomSession) {
    }


    public <T> void sendMessage(WebSocketSession session, T message) {

    }
}
