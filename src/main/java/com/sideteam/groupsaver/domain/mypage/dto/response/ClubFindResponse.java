package com.sideteam.groupsaver.domain.mypage.dto.response;

import com.sideteam.groupsaver.domain.category.domain.DevelopMajor;
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
    private DevelopMajor category;
    private String mainImage;
    private Boolean isStatus;
    private LocalDateTime startClub;
    private ClubActivityType activityType;

    @Builder
    public static ClubFindResponse toDto(Club club) {
        return ClubFindResponse.builder()
                .id(club.getId())
                .name(club.getName())
                .memberCurrentNum(club.getMemberCurrentNum())
                .memberNumMax(club.getMemberNumMax())
                .type(club.getType())
                .description(club.getDescription())
                .category(club.getCategory())
                .mainImage(club.getMainImage())
                .isStatus(club.isStatus())
                .startClub(club.getStartClub())
                .activityType(club.getActivityType())
                .build();
    }
}
