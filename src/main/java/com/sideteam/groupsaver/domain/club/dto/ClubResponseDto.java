package com.sideteam.groupsaver.domain.club.dto;

import com.sideteam.groupsaver.domain.category.domain.ClubCategory;
import com.sideteam.groupsaver.domain.club.domain.Club;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ClubResponseDto {
    private String mainImage;
    private String type;
    private String title;
    private String location;
    private int currentMemberNum;
    private int memberMaxNum;
    private String category;

    public static ClubResponseDto of(Club club) {
        return new ClubResponseDto(club.getMainImage(), club.getType().getClubType(), club.getName(), club.getLocation(), club.getMemberCurrentNum(), club.getMemberNumMax(), club.getCategory().getCategoryType());
    }
    public static List<ClubResponseDto> listOf(List<ClubCategory> clubList) {
        return clubList.stream()
                .filter(clubCategory -> clubCategory.getClub().isStatus())
                .map(clubCategory -> ClubResponseDto.of(clubCategory.getClub()))
                .collect(Collectors.toList());
    }
}
