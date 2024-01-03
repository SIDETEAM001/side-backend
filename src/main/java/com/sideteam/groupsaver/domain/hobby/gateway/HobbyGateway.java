package com.sideteam.groupsaver.domain.hobby.gateway;

import com.sideteam.groupsaver.domain.category.domain.HobbyCategory;
import com.sideteam.groupsaver.domain.category.domain.HobbySub;
import com.sideteam.groupsaver.domain.category.service.HobbyCategoryService;
import com.sideteam.groupsaver.domain.club.domain.ClubMemberRole;
import com.sideteam.groupsaver.domain.club.domain.ClubType;
import com.sideteam.groupsaver.domain.hobby.controller.HobbyController;
import com.sideteam.groupsaver.domain.hobby.domain.Hobby;
import com.sideteam.groupsaver.domain.hobby.domain.HobbyMember;
import com.sideteam.groupsaver.domain.hobby.dto.HobbyCreateDto;
import com.sideteam.groupsaver.domain.hobby.dto.HobbyResponseDto;
import com.sideteam.groupsaver.domain.hobby.service.HobbyMemberService;
import com.sideteam.groupsaver.domain.hobby.service.HobbyService;
import com.sideteam.groupsaver.domain.member.domain.Member;
import com.sideteam.groupsaver.domain.member.service.MemberService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class HobbyGateway {
    private final HobbyService hobbyService;
    private final HobbyCategoryService categoryService;
    private final HobbyMemberService hobbyMemberService;
    private final MemberService memberService;

    public long createHobby(HobbyCreateDto dto) {
        Member member = findMember();
        Hobby hobby = hobbyService.createHobby(dto);
        HobbyCategory category = categoryService.checkACategory(dto.getCategoryType(), hobby);
        hobby.updateCategory(category);
        HobbyMember hobbyMember = hobbyMemberService.createHobbyMember(hobby, member, ClubMemberRole.LEADER);
        addAHobbyMember(hobbyMember, hobby, member);
        return hobby.getId();
    }

    private void addAHobbyMember(HobbyMember hobbyMember, Hobby hobby, Member member) {
        hobby.addAHobbyMember(hobbyMember);
        member.addAHobbyMember(hobbyMember);
    }

    public void joinTheHobby(Long hobbyId) {
        Hobby hobby = hobbyService.checkHobby(hobbyId);
        Member member = findMember();
        HobbyMember hobbyMember = hobbyMemberService.createHobbyMember(hobby, member, ClubMemberRole.MEMBER);
        addAHobbyMember(hobbyMember, hobby, member);
    }

    public List<HobbyResponseDto> findHobbyList(HobbySub category, ClubType type) {
        return categoryService.getHobbyList(category, type);
    }

    public void updateDescriptionOfTheHobby(Long hobbyId, HobbyController.RequestUpdateDescription description) {
        hobbyMemberService.checkAReaders(findMember(), hobbyId);
        hobbyService.updateDescription(hobbyId, description.description());
    }

    public void deleteTheHobby(Long hobbyId) {
        hobbyMemberService.checkAReaders(findMember(), hobbyId);
        hobbyService.deleteHobby(hobbyId);
    }

    public Member findMember() {
        return memberService.findMember();
    }
}
