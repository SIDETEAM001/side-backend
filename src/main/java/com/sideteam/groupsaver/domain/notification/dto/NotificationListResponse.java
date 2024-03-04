package com.sideteam.groupsaver.domain.notification.dto;

import com.sideteam.groupsaver.domain.notification.domain.Notification;
import com.sideteam.groupsaver.domain.notification.domain.NotificationType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class NotificationListResponse {
    private List<NotificationResponse> publicNoti;
    private List<NotificationResponse> privateNoti;

    public static NotificationListResponse of(List<Notification> notifications) {
        List<NotificationResponse> publicNoti = new ArrayList<>();
        List<NotificationResponse> privateNoti = new ArrayList<>();

        notifications.forEach(notification -> {
            NotificationResponse response = NotificationResponse.of(notification.getId(), notification.getTitle(), notification.getBody(), notification.getImage(), notification.getRemoteId(), notification.getRemoteType(), notification.isOpen());
            if (response.getRemoteType().getType() == NotificationType.PUBLIC) {
                publicNoti.add(response);
            } else {
                privateNoti.add(response);
            }
        });
        return new NotificationListResponse(publicNoti, privateNoti);
    }
}
