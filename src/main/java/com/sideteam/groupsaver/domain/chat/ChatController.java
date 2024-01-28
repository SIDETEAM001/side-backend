package com.sideteam.groupsaver.domain.chat;

import com.sideteam.groupsaver.domain.chat.dto.request.ChatRequest;
import com.sideteam.groupsaver.domain.chat.dto.response.ChatResponse;
import com.sideteam.groupsaver.global.resolver.member_info.MemberIdParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

import static com.sideteam.groupsaver.global.websocket.WebSocketUtils.DEFAULT_CLUB_CHAT_PATH;
import static org.springframework.data.domain.Sort.Direction.DESC;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatController {

    private final ChatService chatService;

    @ResponseBody
    @GetMapping("/api/v1/clubs/{clubId}/chats")
    public ResponseEntity<Page<ChatResponse>> getClubChatting(
            @PathVariable(name = "clubId") Long clubId,
            @MemberIdParam Long memberId,
            @PageableDefault(sort = "createAt", direction = DESC) Pageable pageable) {

        return ResponseEntity.ok(chatService.getAllChatsByClub(clubId, memberId, pageable));
    }

    @MessageMapping("/chats/clubs/{clubId}")
    @SendTo(DEFAULT_CLUB_CHAT_PATH + "{clubId}")
    public ChatResponse sendMessage(@DestinationVariable("clubId") Long clubId,
                                    @Header("simpSessionAttributes") Map<String, Object> simpSessionAttributes,
                                    @Payload ChatRequest chatRequest
    ) {
        log.debug("모임: {}, {}, re: {}", clubId, simpSessionAttributes, chatRequest);
        return chatService.save(chatRequest, clubId, simpSessionAttributes);
    }

}
