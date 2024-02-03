package com.sideteam.groupsaver.domain.notification.repository;

import com.sideteam.groupsaver.domain.notification.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
