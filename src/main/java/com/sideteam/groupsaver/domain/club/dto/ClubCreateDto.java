package com.sideteam.groupsaver.domain.club.dto;

import com.sideteam.groupsaver.domain.club.domain.ClubActivityType;
import com.sideteam.groupsaver.domain.club.domain.ClubMajor;
import com.sideteam.groupsaver.domain.club.domain.ClubType;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ClubCreateDto {
    private ClubMajor category;
    private ClubType type;
    private String name;
    private ClubActivityType activityType;
    private int memberNumMax;
    private LocalDateTime startClub;
    private String mainImage;
    private String description;
    private String location;
}
