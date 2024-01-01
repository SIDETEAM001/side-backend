package com.sideteam.groupsaver.domain.club.domain;

import com.sideteam.groupsaver.domain.category.domain.DevelopMajor;
import com.sideteam.groupsaver.domain.category.domain.HobbySub;
import com.sideteam.groupsaver.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubHobby extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    // 개설한 본인을 포함하여 시작 인원을 1로 고정
    private int memberCurrentNum = 1;
    private int memberNumMax;
    @Enumerated(value = EnumType.STRING)
    private ClubType type;
    private String description;
    @Enumerated(value = EnumType.STRING)
    private HobbySub category;
    @Column(name = "main_image")
    private String mainImage;
    @Column(name = "is_status")
    private boolean isStatus = Boolean.TRUE;
    @Column(name = "start_club")
    private LocalDateTime startClub;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "activity_type")
    private ClubActivityType activityType;
}