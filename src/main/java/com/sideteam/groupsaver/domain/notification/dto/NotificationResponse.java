package com.sideteam.groupsaver.domain.notification.dto;

import com.sideteam.groupsaver.domain.notification.domain.Notification;
import com.sideteam.groupsaver.domain.notification.domain.NotificationRemoteType;
import com.sideteam.groupsaver.domain.notification.domain.NotificationType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class NotificationResponse {
    private long id;
    private String title;
    private String body;
    private String image;
    private long remoteId;
    private NotificationRemoteType remoteType;
    private boolean isOpen;

    public static NotificationResponse of(long id, String title, String body, String image, long remoteId, NotificationRemoteType remoteType, boolean isOpen) {
        return NotificationResponse.builder()
                .id(id)
                .title(title)
                .body(body)
                .image(image)
                .remoteId(remoteId)
                .remoteType(remoteType)
                .isOpen(isOpen)
                .build();
    }
}
