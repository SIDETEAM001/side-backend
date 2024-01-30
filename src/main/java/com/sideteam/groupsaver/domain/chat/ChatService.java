package com.sideteam.groupsaver.domain.chat;

import com.sideteam.groupsaver.domain.chat.dto.WebSocketIdentity;
import com.sideteam.groupsaver.domain.chat.dto.request.ChatRequest;
import com.sideteam.groupsaver.domain.chat.dto.response.ChatResponse;
import com.sideteam.groupsaver.domain.club.domain.Club;
import com.sideteam.groupsaver.domain.member.domain.Member;
import com.sideteam.groupsaver.global.websocket.WebSocketUtils;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ChatService {

    private final ChatRepository chatRepository;
    private final EntityManager entityManager;

    @Transactional
    public ChatResponse save(ChatRequest chatRequest, Long clubId, Map<String, Object> header) {
        Chat chat = chatRepository.save(toChat(chatRequest, clubId));

        return toChatResponse(chat, header);
    }

    @PreAuthorize("@authorityChecker.hasAuthority(#memberId, @clubMemberRepository.isInClub(#clubId, #memberId))")
    public Page<ChatResponse> getAllChatsByClub(Long clubId, Long memberId, Pageable pageable) {
        return chatRepository.findAllByClubId(clubId, pageable).map(ChatResponse::of);
    }


    private Chat toChat(ChatRequest chatRequest, Long clubId) {
        log.info("{}", chatRequest);
        return Chat.builder()
                .content(chatRequest.content())
                .type(chatRequest.type())
                .club(entityManager.getReference(Club.class, clubId))
                .sender(entityManager.getReference(Member.class, chatRequest.memberId()))
                .build();
    }


    private ChatResponse toChatResponse(Chat chat, Map<String, Object> header) {
        WebSocketIdentity identity = getValueFromHeader(header, WebSocketUtils.IDENTITY);

        return ChatResponse.builder()
                .nickname(identity.nickname())
                .profileImgUrl(identity.profileUrl())
                .type(chat.getType())
                .content(chat.getContent())
                .createdAt(chat.getCreatedAtLocalDateTime())
                .build();
    }

    private <T> T getValueFromHeader(Map<String, Object> header, String key) {
        return (T) header.get(key);
    }

}
