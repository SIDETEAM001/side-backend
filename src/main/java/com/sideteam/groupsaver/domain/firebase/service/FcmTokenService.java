package com.sideteam.groupsaver.domain.firebase.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.sideteam.groupsaver.domain.firebase.domain.FcmToken;
import com.sideteam.groupsaver.domain.firebase.dto.CreateFcmTokenDto;
import com.sideteam.groupsaver.domain.firebase.repository.FcmTokenRepository;
import com.sideteam.groupsaver.domain.member.service.MemberService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class FcmTokenService {
    private final FcmTokenRepository fcmRepository;
    private final FirebaseMessaging firebaseMessaging;
    private final MemberService memberService;

    public void createFcmToken(CreateFcmTokenDto dto) {
        if (!fcmRepository.existsByToken(dto.getToken())) {
            fcmRepository.save(FcmToken.of(dto.getEmail(), dto.getToken()));
        }
    }

    public void sendNotification(String title, String body) {
        List<String> tokenList = fcmRepository.findAllTokenByEmail(memberService.findMember().getEmail());
        tokenList.forEach(token -> {
            try {
                fcm_transmit(token, title, body);
            } catch (FirebaseMessagingException f1) {
                log.error(f1.toString());
            }
        });
    }

    public void fcm_transmit(String token, String title, String body) throws FirebaseMessagingException {
        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build();

        Message message = Message.builder()
                .setNotification(notification)
                .putData("url", "localhost:8080")
                .setToken(token)
                .build();
        firebaseMessaging.send(message);
    }
}