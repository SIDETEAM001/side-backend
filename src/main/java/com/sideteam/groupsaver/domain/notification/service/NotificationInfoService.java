package com.sideteam.groupsaver.domain.notification.service;

import com.sideteam.groupsaver.domain.notification.domain.Notification;
import com.sideteam.groupsaver.domain.notification.dto.NotificationListResponse;
import com.sideteam.groupsaver.domain.notification.repository.NotificationRepository;
import com.sideteam.groupsaver.global.auth.userdetails.GetAuthUserUtils;
import com.sideteam.groupsaver.global.exception.BusinessException;
import com.sideteam.groupsaver.global.exception.notification.NotificationErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationInfoService {
    private final NotificationRepository notificationRepository;

    @Transactional(readOnly = true)
    public NotificationListResponse getAllNotification() {
        long memberId = GetAuthUserUtils.getAuthUserId();
        List<Notification> notifications = notificationRepository.findAllByMember(memberId);
        return NotificationListResponse.of(notifications);
    }

    public void checkNotification(long notificationId) {
        Notification notification = notificationRepository.findById(notificationId).orElseThrow(() -> new BusinessException(NotificationErrorCode.NOT_FOUND_NOTIFICATION, NotificationErrorCode.NOT_FOUND_NOTIFICATION.getDetail()));
        notification.updateIsOpen();
    }
}
