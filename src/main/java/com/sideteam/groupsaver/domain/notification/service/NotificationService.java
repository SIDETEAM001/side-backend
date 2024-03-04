package com.sideteam.groupsaver.domain.notification.service;

import com.sideteam.groupsaver.domain.club.domain.Club;
import com.sideteam.groupsaver.domain.club.repository.ClubMemberRepository;
import com.sideteam.groupsaver.domain.firebase.service.FcmTokenService;
import com.sideteam.groupsaver.domain.join.repository.WantClubCategoryRepository;
import com.sideteam.groupsaver.domain.member.domain.Member;
import com.sideteam.groupsaver.domain.member.repository.MemberRepository;
import com.sideteam.groupsaver.domain.notification.domain.Notification;
import com.sideteam.groupsaver.domain.notification.domain.ADMessage;
import com.sideteam.groupsaver.domain.notification.domain.NotificationRemoteType;
import com.sideteam.groupsaver.domain.notification.repository.ADMessageRepository;
import com.sideteam.groupsaver.domain.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final WantClubCategoryRepository wantClubCategoryRepository;
    private final MemberRepository memberRepository;
    private final FcmTokenService fcmTokenService;
    private final ADMessageRepository adMessageRepository;

    @Async("threadPoolTaskExecutor")
    public void createNewSchedule(long clubId, long scheduleId, String image, Long creatorId) {
        List<Member> clubMembers = clubMemberRepository.findAllMembersExceptCreatorByClubId(clubId, creatorId);
        createNotifications(clubMembers, NotificationRemoteType.SCHEDULE.getMessage(), null, image, scheduleId, NotificationRemoteType.SCHEDULE);
        sendPushAlarm(clubMembers, NotificationRemoteType.SCHEDULE.getMessage());
    }

    @Async("threadPoolTaskExecutor")
    public void createNewMember(long clubId, long newMemberId, String image) {
        List<Member> clubMembers = clubMemberRepository.findAllMembersExceptNewMemberByClubId(clubId, newMemberId);
        createNotifications(clubMembers, NotificationRemoteType.NEW_MEMBER.getMessage(), null, image, clubId, NotificationRemoteType.NEW_MEMBER);
        sendPushAlarm(clubMembers, NotificationRemoteType.NEW_MEMBER.getMessage());
    }

    @Async("threadPoolTaskExecutor")
    public void createNewClub(Club club, Long creatorId) {
        List<Member> members = wantClubCategoryRepository.findAllMembersExceptCreatorByMajor(club.getCategoryMajor(), creatorId);
        createNotifications(members, club.getName(), club.getDescription(), club.getMainImage(), club.getId(), NotificationRemoteType.NEW_CLUB);
        sendPushAlarm(members, NotificationRemoteType.NEW_CLUB.getMessage());
    }

    @Scheduled(cron = "0 30 18 1 * *")
    public void createAD() {
        log.info("광고 시작");
        ADMessage message = getRandomADMessage();
        List<Member> members = memberRepository.findAllByActiveStatusIsActive();
        createNotifications(members, message.getMessage(), null, "https://sabuzac-bucket.s3.ap-northeast-2.amazonaws.com/icon/logo_default_image.png",0, NotificationRemoteType.AD);
        sendPushAlarm(members, message.getMessage());
        log.info("광고 알림 완료");
    }

    private ADMessage getRandomADMessage() {
        List<ADMessage> messages = adMessageRepository.findAll();
        return messages.get((int) (Math.random() * messages.size()));
    }

    private void createNotifications(List<Member> members, String title, String body, String image, long remoteId, NotificationRemoteType remoteType) {
        notificationRepository.saveAll(members.stream().map(member -> Notification.of(title, body, image, remoteId, remoteType, member)).collect(Collectors.toList()));
    }

    private void sendPushAlarm(List<Member> members, String message) {
        members.forEach(member -> fcmTokenService.sendPushAlarm(member, message));
    }


}
