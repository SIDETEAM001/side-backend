package com.sideteam.groupsaver.domain.firebase.service;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.sideteam.groupsaver.utils.context.IntegrationSpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@IntegrationSpringBootTest
class FcmTokenServiceTest {

    @Autowired
    FcmTokenService fcmTokenService;

    @Test
    void fcmTransmit() throws FirebaseMessagingException {
//        fcmTokenService.fcmTransmit("dm4ZDIhzSCykNyVhFy6xQL:APA91bFUUK46hc_catsZuSKq8YOTGeURhkM044-4bbETNQZPtrM19tanPKB3wobmKLNn6xeSJF3q9xCATuIoQ0WsVzNKo5X-BMYnyOyzwwKTFpElzhtgBkLRkJKIz-zVOOOxcWuSqVR0",
//                "test", "test");
        // 올바른 토큰 만들때까지 보류
    }
}
