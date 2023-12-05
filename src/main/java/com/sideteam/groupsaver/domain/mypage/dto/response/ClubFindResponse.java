package com.sideteam.groupsaver.domain.mypage.dto.response;

import com.sideteam.groupsaver.domain.mypage.domain.Club;
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

    @Builder
    public static ClubFindResponse toDto(Club club) {
        return ClubFindResponse.builder()
                .id(club.getId())
                .name(club.getName())
                .location(club.getLocation())
                .memberNumMax(club.getMemberNumMax())
                .type(club.getType())
                .category(club.getCategory())
                .mainImage(club.getMainImage())
                .status(club.getStatus())
                .period(club.getPeriod())
                .startClub(club.getStartClub())
                .activityType(club.getActivityType())
                .build();
    }
}
