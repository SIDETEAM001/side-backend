package com.sideteam.groupsaver.domain.firebase.service;

import com.google.firebase.messaging.FirebaseMessagingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FcmTokenServiceTest {

    @Autowired
    FcmTokenService fcmTokenService;

    @Test
    void fcm_transmit() throws FirebaseMessagingException {
        fcmTokenService.fcm_transmit("dm4ZDIhzSCykNyVhFy6xQL:APA91bFUUK46hc_catsZuSKq8YOTGeURhkM044-4bbETNQZPtrM19tanPKB3wobmKLNn6xeSJF3q9xCATuIoQ0WsVzNKo5X-BMYnyOyzwwKTFpElzhtgBkLRkJKIz-zVOOOxcWuSqVR0",
                "test", "test", "localhost:8080");
    }
}