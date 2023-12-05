package com.sideteam.groupsaver.domain.mypage.domain;

import com.sideteam.groupsaver.domain.common.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Entity
public class Club extends BaseTimeEntity { // Enum 사용 전
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String location;
    private Long memberNumMax;
    private String type;
    private String description;
    private Long category;
    private String mainImage;
    private Boolean status;
    private String period;
    private LocalDateTime startClub;
    private String activityType;
}

