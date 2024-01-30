package com.sideteam.groupsaver.global.websocket;

import com.sideteam.groupsaver.domain.chat.dto.WebSocketIdentity;
import com.sideteam.groupsaver.domain.club.repository.ClubMemberRepository;
import com.sideteam.groupsaver.domain.member.domain.Member;
import com.sideteam.groupsaver.domain.member.repository.MemberRepository;
import com.sideteam.groupsaver.global.auth.jwt.JwtTokenProvider;
import com.sideteam.groupsaver.global.exception.BusinessException;
import com.sideteam.groupsaver.global.exception.club.ClubErrorCode;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.sideteam.groupsaver.global.websocket.WebSocketUtils.DEFAULT_CLUB_CHAT_PATH;

@RequiredArgsConstructor
@Slf4j
@Component
public class WebSocketAuthInterceptor implements ChannelInterceptor {

    private final MemberRepository memberRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public Message<?> preSend(@Nonnull Message<?> message, @Nonnull MessageChannel channel) {
        final StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        Objects.requireNonNull(accessor, "accessor is null");

        final StompCommand stompCommand = accessor.getCommand();
        Objects.requireNonNull(stompCommand, "stompCommand is null");

        switch (stompCommand) {
            case CONNECT:
            case SEND:
                setIdentity(accessor);
                break;

            case SUBSCRIBE:
                handleSubscribe(accessor);
                break;

            case DISCONNECT:
                handleDisconnect(accessor);
                break;
        }

        return message;
    }


    private void setIdentity(final StompHeaderAccessor accessor) {
        Member member = getMemberFromToken(accessor.getFirstNativeHeader(HttpHeaders.AUTHORIZATION));
        log.info("MEMBER: {}, {}", member, accessor.getFirstNativeHeader(HttpHeaders.AUTHORIZATION));
        WebSocketIdentity identity = new WebSocketIdentity(member.getId(), member.getNickname(), member.getProfileUrl());
        WebSocketUtils.setValue(accessor, WebSocketUtils.IDENTITY, identity);
    }

    private void handleSubscribe(final StompHeaderAccessor accessor) {
        WebSocketIdentity identity = WebSocketUtils.getValue(accessor, WebSocketUtils.IDENTITY);
        Long clubId = parseClubIdFromPath(accessor);
        log.info("identity : {} clubId : {}", identity.memberId(), clubId);
        WebSocketUtils.setValue(accessor, "clubId", clubId);
        validateMemberInClub(identity.memberId(), clubId);
    }

    private void handleDisconnect(final StompHeaderAccessor accessor) {
        WebSocketIdentity identity = WebSocketUtils.getValue(accessor, WebSocketUtils.IDENTITY);
        log.info("DISCONNECT {}", identity);
    }

    private String getToken(String tokenHeader) {
        return JwtTokenProvider.parseJwt(tokenHeader);
    }

    private Member getMemberFromToken(String tokenHeader) {
        String accessToken = getToken(tokenHeader);
        String subject = jwtTokenProvider.getSubject(accessToken);

        return memberRepository.findByIdOrThrow(Long.parseLong(subject));
    }


    private void validateMemberInClub(Long memberId, Long clubId) {
        if (!clubMemberRepository.existsByClubIdAndMemberId(clubId, memberId)) {
            throw new BusinessException(ClubErrorCode.CLUB_MEMBER_DO_NOT_HAVE_PERMISSION, memberId.toString());
        }
    }

    private Long parseClubIdFromPath(StompHeaderAccessor accessor) {
        String destination = accessor.getDestination();
        if (destination == null || !destination.startsWith(DEFAULT_CLUB_CHAT_PATH)) {
            throw new IllegalArgumentException("Invalid destination path");
        }
        try {
            return Long.parseLong(destination.substring(DEFAULT_CLUB_CHAT_PATH.length()));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid club ID in the path");
        }
    }

}
