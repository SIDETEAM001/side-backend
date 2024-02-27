package com.sideteam.groupsaver.domain.notification.repository;

import com.sideteam.groupsaver.domain.notification.domain.ADMessage;
import com.sideteam.groupsaver.domain.notification.domain.NotificationRemoteType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ADMessageRepository extends JpaRepository<ADMessage, Long> {
}
