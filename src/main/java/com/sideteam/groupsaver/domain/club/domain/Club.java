package com.sideteam.groupsaver.domain.club.domain;

import com.sideteam.groupsaver.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Club extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private int memberCurrentNum = 1;
    private int memberNumMax;
    @Enumerated(value = EnumType.STRING)
    private ClubType type;
    private String description;
    private long category;
    @Column(name = "main_image")
    private String mainImage = "defaultImage";
    private boolean status = true;
    private String period;
    @Column(name = "start_club")
    private LocalDateTime startClub;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "activity_type")
    private ClubActivityType activityType;

    private Club(String name, int memberNumMax, ClubType type, String description, long category, String period, LocalDateTime startClub, ClubActivityType activityType) {
        this.name = name;
        this.memberNumMax = memberNumMax;
        this.type = type;
        this.description = description;
        this.category = category;
        this.period = period;
        this.startClub = startClub;
        this.activityType = activityType;
    }

    public static Club of(String name, int memberNumMax, ClubType type, String description, long category, String period, LocalDateTime startClub, ClubActivityType activityType) {
        return new Club(name, memberNumMax, type, description, category, period, startClub, activityType);
    }

    public void updateImage(String mainImage) {
        this.mainImage = mainImage;
    }
}