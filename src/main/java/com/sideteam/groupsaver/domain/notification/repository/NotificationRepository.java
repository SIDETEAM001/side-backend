package com.sideteam.groupsaver.domain.notification.repository;

import com.sideteam.groupsaver.domain.notification.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Query("SELECT n FROM Notification n WHERE n.member.id = :memberId")
    List<Notification> findAllByMember(long memberId);
}
