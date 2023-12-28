package com.sideteam.groupsaver.domain.club.dto;

import com.sideteam.groupsaver.domain.club.domain.ClubActivityType;
import com.sideteam.groupsaver.domain.category.domain.DevelopMajor;
import com.sideteam.groupsaver.domain.club.domain.ClubType;
import lombok.Getter;

@Getter
public class ClubCreateDto {
    private DevelopMajor categoryType;
    private ClubType type;
    private String name;
    private ClubActivityType activityType;
    private int memberNumMax;
    private String startClub;
    private String mainImage;
    private String description;
    private String location;
}
