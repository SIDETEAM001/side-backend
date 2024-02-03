package com.sideteam.groupsaver.domain.notification.dto;

import com.sideteam.groupsaver.domain.notification.domain.Notification;
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
    private String title;
    private String body;
    private String image;
    private long remoteId;
    private NotificationType type;

    public static NotificationResponse of(String title, String body, String image, long remoteId, NotificationType type) {
        return NotificationResponse.builder()
                .title(title)
                .body(body)
                .image(image)
                .remoteId(remoteId)
                .type(type)
                .build();
    }

    public static List<NotificationResponse> listOf(List<Notification> notifications) {
        return notifications.stream().map(notification ->
                NotificationResponse.of(
                        notification.getTitle(),
                        notification.getBody(),
                        notification.getImage(),
                        notification.getRemoteId(),
                        notification.getType())).collect(Collectors.toList());
    }
}
