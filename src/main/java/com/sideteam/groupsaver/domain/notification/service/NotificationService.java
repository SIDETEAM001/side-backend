package com.sideteam.groupsaver.domain.notification.service;

import com.sideteam.groupsaver.domain.club.repository.ClubMemberRepository;
import com.sideteam.groupsaver.domain.firebase.service.FcmTokenService;
import com.sideteam.groupsaver.domain.member.domain.Member;
import com.sideteam.groupsaver.domain.notification.domain.Notification;
import com.sideteam.groupsaver.domain.notification.domain.NotificationType;
import com.sideteam.groupsaver.domain.notification.domain.PushAlarmMessage;
import com.sideteam.groupsaver.domain.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final FcmTokenService fcmTokenService;

    public void createMyClubNotification(String title, String body, String image, long remoteId, NotificationType type) {
        List<Member> clubMembers = clubMemberRepository.findAllMembersByClubId(remoteId);
        notificationRepository.saveAll(
            clubMembers.stream().map(member -> {
                Notification notification = Notification.of(title, body, image, remoteId, type, member);
                member.addNotification(notification);
                fcmTokenService.sendPushAlarm(member, choiceMyClubPushAlarmMessage(type));
                return notification;
            }).toList());
    }

    private String choiceMyClubPushAlarmMessage(NotificationType type) {
        if (type == NotificationType.CLUB_SCHEDULE) {
            return PushAlarmMessage.CLUB_SCHEDULE.getMessage();
        } else {
            return PushAlarmMessage.CLUB_NEW_MEMBER.getMessage();
        }
    }
}
