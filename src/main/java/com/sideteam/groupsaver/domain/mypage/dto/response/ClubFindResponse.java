package com.sideteam.groupsaver.domain.mypage.dto.response;

import com.sideteam.groupsaver.domain.category.domain.ClubCategoryMajor;
import com.sideteam.groupsaver.domain.club.domain.Club;
import com.sideteam.groupsaver.domain.club.domain.ClubActivityType;
import com.sideteam.groupsaver.domain.club.domain.ClubType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClubFindResponse {
    private Long id;
    private String name;
    private Integer memberCurrentNum;
    private Integer memberNumMax;
    private ClubType type;
    private String description;
    private ClubCategoryMajor category;
    private String mainImage;
    private Boolean isStatus;
    private LocalDateTime startClub;
    private ClubActivityType activityType;

    public static ClubFindResponse toDto(Club club) {
        return ClubFindResponse.builder()
                .id(club.getId())
                .name(club.getName())
                .memberCurrentNum(club.getMemberCurrentNumber())
                .memberNumMax(club.getMemberMaxNumber())
                .type(club.getType())
                .description(club.getDescription())
                .category(club.getCategoryMajor())
                .mainImage(club.getMainImage())
                .isStatus(club.isActive())
                .startClub(club.getStartAt())
                .activityType(club.getActivityType())
                .build();
    }
}
