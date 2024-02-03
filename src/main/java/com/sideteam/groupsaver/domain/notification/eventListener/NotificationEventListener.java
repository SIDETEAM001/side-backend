package com.sideteam.groupsaver.domain.notification.eventListener;

import com.sideteam.groupsaver.domain.club.domain.ClubMember;
import com.sideteam.groupsaver.domain.club_schedule.domain.ClubSchedule;
import com.sideteam.groupsaver.domain.notification.domain.NotificationType;
import com.sideteam.groupsaver.domain.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationEventListener {
    private final NotificationService notificationService;

    @EventListener
    public void sendMyClubScheduleNoti(ClubSchedule clubSchedule) {
        System.out.println("일정 알림");
        notificationService.createMyClubNotification("모임에 새로운 일정이 등록되었습니다", null,clubSchedule.getClub().getMainImage(), clubSchedule.getId(), NotificationType.CLUB_SCHEDULE);
    }

    @EventListener
    public void sendMyClubNewMemberNoti(ClubMember clubMember) {
        System.out.println("새로운 멤버");
        notificationService.createMyClubNotification("모임에 새로운 멤버가 들어왔습니다", null, clubMember.getClub().getMainImage(), clubMember.getClub().getId(), NotificationType.NEW_MEMBER);
    }
}
