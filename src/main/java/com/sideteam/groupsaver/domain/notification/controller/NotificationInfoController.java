package com.sideteam.groupsaver.domain.notification.controller;

import com.sideteam.groupsaver.domain.notification.dto.NotificationListResponse;
import com.sideteam.groupsaver.domain.notification.service.NotificationInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notification")
@RequiredArgsConstructor
public class NotificationInfoController {
    private final NotificationInfoService notificationInfoService;

    @GetMapping
    public ResponseEntity<NotificationListResponse> getAllNotification() {
        return ResponseEntity.ok(notificationInfoService.getAllNotification());
    }

    @PostMapping("/open/{notificationId}")
    public ResponseEntity<String> checkNotification(@PathVariable("notificationId") long notificationId) {
        notificationInfoService.checkNotification(notificationId);
        return ResponseEntity.ok("OK");
    }
}
