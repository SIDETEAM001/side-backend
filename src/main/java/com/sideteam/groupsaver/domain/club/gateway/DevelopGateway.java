package com.sideteam.groupsaver.domain.club.gateway;

import com.sideteam.groupsaver.domain.category.domain.ClubCategory;
import com.sideteam.groupsaver.domain.category.domain.DevelopMajor;
import com.sideteam.groupsaver.domain.category.service.CategoryService;
import com.sideteam.groupsaver.domain.club.controller.ClubController;
import com.sideteam.groupsaver.domain.club.domain.Club;
import com.sideteam.groupsaver.domain.club.domain.ClubMemberRole;
import com.sideteam.groupsaver.domain.club.domain.ClubType;
import com.sideteam.groupsaver.domain.club.dto.ClubCreateDto;
import com.sideteam.groupsaver.domain.club.dto.ClubResponseDto;
import com.sideteam.groupsaver.domain.club.service.ClubMemberService;
import com.sideteam.groupsaver.domain.club.service.ClubService;
import com.sideteam.groupsaver.domain.member.domain.Member;
import com.sideteam.groupsaver.domain.member.service.MemberService;
import com.sideteam.groupsaver.global.auth.userdetails.GetAuthUserUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DevelopGateway {
    private final ClubService clubService;
    private final CategoryService categoryService;
    private final ClubMemberService clubMemberService;
    private final MemberService memberService;

    public long createClub(ClubCreateDto dto) {
        Member member = findMember();
        Club club = clubService.createClub(dto);
        ClubCategory category = categoryService.checkACategory(dto.getCategoryType(), club);
        club.updateCategory(category);
        clubMemberService.createClubMember(club, member, ClubMemberRole.LEADER);
        return club.getId();
    }

    public void joinTheClub(Long clubId) {
        Club club = clubService.checkClub(clubId);
        Member member = findMember();
        clubMemberService.createClubMember(club, member, ClubMemberRole.MEMBER);
    }

    public List<ClubResponseDto> findDevelopClubList(DevelopMajor category, ClubType type) {
        return categoryService.getDevelopClubList(category, type);
    }

    public void updateDescriptionOfTheClub(Long clubId, ClubController.RequestUpdateDescription description) {
       clubMemberService.checkAReaders(GetAuthUserUtils.getAuthUserId(), clubId);
       clubService.updateDescription(clubId, description.description());
    }

    public void deleteTheClub(Long clubId) {
        clubMemberService.checkAReaders(GetAuthUserUtils.getAuthUserId(), clubId);
        clubService.deleteClub(clubId);
    }

    public Member findMember() {
        return memberService.findMember();
    }
}