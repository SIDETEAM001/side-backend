package com.sideteam.groupsaver.domain.hobby.dto;

import com.sideteam.groupsaver.domain.club.domain.Club;
import com.sideteam.groupsaver.domain.club.domain.ClubType;
import com.sideteam.groupsaver.domain.club.dto.ClubResponseDto;
import com.sideteam.groupsaver.domain.hobby.domain.Hobby;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class HobbyResponseDto {
    private String mainImage;
    private String type;
    private String title;
    private String location;
    private int currentMemberNum;
    private int memberMaxNum;
    private String category;

    public static HobbyResponseDto of(Hobby hobby) {
        return new HobbyResponseDto(hobby.getMainImage(), hobby.getType().getClubType(), hobby.getName(), hobby.getLocation(), hobby.getMemberCurrentNum(), hobby.getMemberNumMax(),
                hobby.getHobbyCategory().getSub().getCategory());
    }
    public static List<HobbyResponseDto> listOf(List<Hobby> hobbyList, ClubType type) {
        return hobbyList.stream()
                .filter(club -> club.isStatus() && club.getType() == type)
                .map(HobbyResponseDto::of)
                .collect(Collectors.toList());
    }
}
