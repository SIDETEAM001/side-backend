package com.sideteam.groupsaver.domain.firebase.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.sideteam.groupsaver.domain.firebase.domain.FcmToken;
import com.sideteam.groupsaver.domain.firebase.dto.CreateFcmTokenDto;
import com.sideteam.groupsaver.domain.firebase.repository.FcmTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FcmTokenService {
    private final FcmTokenRepository fcmRepository;
    private final FirebaseMessaging firebaseMessaging;

    public void createFcmToken(CreateFcmTokenDto dto) {
        if (fcmRepository.existsByEmail(dto.getEmail())) {
            fcmRepository.findByEmail(dto.getEmail()).updateToken(dto.getToken());
        } else {
            fcmRepository.save(FcmToken.of(dto.getEmail(), dto.getToken()));
        }
    }

    public void fcm_transmit(String token, String title, String body, String deepLink) throws FirebaseMessagingException {
        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build();

        Message message = Message.builder()
                .setNotification(notification)
                .putData("url", deepLink)
                .setToken(token)
                .build();
        firebaseMessaging.send(message);
    }
}