package com.sideteam.groupsaver.domain.notification.domain;

import com.sideteam.groupsaver.domain.common.BaseTimeEntity;
import com.sideteam.groupsaver.domain.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String body;
    private String image;
    private long remoteId;
    @Enumerated(value = EnumType.STRING)
    private NotificationRemoteType remoteType;
    private boolean isOpen;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Notification(String title, String body, String image, long remoteId, NotificationRemoteType remoteType,Member member) {
        this.title = title;
        this.body = body;
        this.image = image;
        this.remoteId = remoteId;
        this.remoteType = remoteType;
        this.member = member;
        this.isOpen = false;
    }

    public static Notification of(String title, String body, String image, long remoteId, NotificationRemoteType remoteType ,Member member) {
        return Notification.builder()
                .title(title)
                .body(body)
                .image(image)
                .remoteId(remoteId)
                .member(member)
                .remoteType(remoteType)
                .build();
    }

    public void updateIsOpen() {
        this.isOpen = true;
    }
}
