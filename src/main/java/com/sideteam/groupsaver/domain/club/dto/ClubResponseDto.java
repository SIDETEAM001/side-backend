package com.sideteam.groupsaver.domain.club.dto;

import com.sideteam.groupsaver.domain.club.domain.Club;
import com.sideteam.groupsaver.domain.club.domain.ClubType;
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
        return new ClubResponseDto(club.getMainImage(), club.getType().getClubType(), club.getName(), club.getLocation(), club.getMemberCurrentNum(), club.getMemberNumMax(),
                club.getClubCategory().getMajor().getCategoryType());
    }
    public static List<ClubResponseDto> listOf(List<Club> clubList, ClubType type) {
        return clubList.stream()
                .filter(club -> club.isStatus() && club.getType() == type)
                .map(ClubResponseDto::of)
                .collect(Collectors.toList());
    }
}
