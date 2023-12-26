package com.sideteam.groupsaver.domain.club.domain;

import com.sideteam.groupsaver.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;


import java.time.LocalDateTime;

@Entity
// @SQLDelete를 사용하면 컬럼을 인식못하는 오류가 발생함
//@SQLDelete(sql = "UPDATE club SET isStatus = false WHERE id = ?")
//@Where(clause = "isStatus = true")
@Getter
@NoArgsConstructor
public class Club extends BaseTimeEntity {
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
    private ClubMajor category;
    @Column(name = "main_image")
    private String mainImage;
    @Column(name = "is_status")
    private boolean isStatus = Boolean.TRUE;
    // 장단기 모임 기간의 설정칸이 없기에 보류
    //private String period;
    @Column(name = "start_club")
    private LocalDateTime startClub;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "activity_type")
    private ClubActivityType activityType;

    private Club(String name, int memberNumMax, ClubType type, String description, ClubMajor category, String mainImage, LocalDateTime startClub, ClubActivityType activityType) {
        this.name = name;
        this.memberNumMax = memberNumMax;
        this.type = type;
        this.description = description;
        this.category = category;
        this.mainImage = mainImage;
        this.startClub = startClub;
        this.activityType = activityType;
    }

    private Club(String name, int memberNumMax, ClubType type, String description, ClubMajor category, LocalDateTime startClub, ClubActivityType activityType) {
        this.name = name;
        this.memberNumMax = memberNumMax;
        this.type = type;
        this.description = description;
        this.category = category;
        this.startClub = startClub;
        this.activityType = activityType;
    }

    public static Club of(String name, int memberNumMax, ClubType type, String description, ClubMajor category, String mainImage, LocalDateTime startClub, ClubActivityType activityType) {
        return new Club(name, memberNumMax, type, description, category, mainImage, startClub, activityType);
    }

    public void updateImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public void updateDescription(String description) {
        this.description = description;
    }
}
