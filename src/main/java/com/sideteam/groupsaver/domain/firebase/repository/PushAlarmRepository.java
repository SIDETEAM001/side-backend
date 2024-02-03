package com.sideteam.groupsaver.domain.firebase.repository;

import com.sideteam.groupsaver.domain.firebase.domain.PushAlarm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PushAlarmRepository extends JpaRepository<PushAlarm, Long> {
}
