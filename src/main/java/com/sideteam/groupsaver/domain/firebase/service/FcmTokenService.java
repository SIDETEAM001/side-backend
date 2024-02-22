package com.sideteam.groupsaver.domain.firebase.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.sideteam.groupsaver.domain.firebase.domain.FcmToken;
import com.sideteam.groupsaver.domain.firebase.dto.CreateFcmTokenRequest;
import com.sideteam.groupsaver.domain.firebase.dto.DeleteFcmTokenRequest;
import com.sideteam.groupsaver.domain.firebase.repository.FcmTokenRepository;
import com.sideteam.groupsaver.domain.member.domain.Member;
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

    public void createFcmToken(CreateFcmTokenRequest dto) {
        if (!fcmRepository.existsByToken(dto.getToken())) {
            fcmRepository.save(FcmToken.of(dto.getEmail(), dto.getToken()));
        }
    }

    public void sendPushAlarm(Member member, String body) {
        List<FcmToken> tokenList = fcmRepository.findAllByEmail(member.getEmail());
        tokenList.forEach(FCM -> {
            try {
                fcmTransmit(FCM.getToken(), body);
            } catch (FirebaseMessagingException e) {
                log.error("푸쉬 알람 에러 발생 : ", e);
                throw new RuntimeException(e);
            } catch (Exception e1) {
                log.error("푸쉬 알람 에러 발생 : ", e1);
            }
        });
    }

    public void fcmTransmit(String token, String body) throws FirebaseMessagingException {
        Notification notification = Notification.builder()
                .setTitle("사부작")
                .setBody(body)
                .build();

        Message message = Message.builder()
                .setNotification(notification)
                .putData("url", "localhost:8080")
                .setToken(token)
                .build();
        firebaseMessaging.send(message);
    }

    public void deleteFcmToken(DeleteFcmTokenRequest request) {
        fcmRepository.deleteByToken(request.getToken());
    }
}
