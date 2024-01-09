package com.sideteam.groupsaver.domain.hobby.service;

import com.sideteam.groupsaver.domain.club.domain.Club;
import com.sideteam.groupsaver.domain.club.domain.ClubMember;
import com.sideteam.groupsaver.domain.club.domain.ClubMemberRole;
import com.sideteam.groupsaver.domain.club.repository.ClubMemberRepository;
import com.sideteam.groupsaver.domain.hobby.domain.Hobby;
import com.sideteam.groupsaver.domain.hobby.domain.HobbyMember;
import com.sideteam.groupsaver.domain.hobby.repository.HobbyMemberRepository;
import com.sideteam.groupsaver.domain.member.domain.Member;
import com.sideteam.groupsaver.global.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.sideteam.groupsaver.global.exception.club.ClubErrorCode.CLUB_MEMBER_ALREADY_EXIST;
import static com.sideteam.groupsaver.global.exception.club.ClubScheduleErrorCode.MEMBER_DO_NOT_HAVE_PERMISSION;

@Service
@RequiredArgsConstructor
public class HobbyMemberService {
    private final HobbyMemberRepository hobbyMemberRepository;

    public HobbyMember createHobbyMember(Hobby hobby, Member member, ClubMemberRole role) {
        if (hobbyMemberRepository.existsByMemberIdAndHobbyId(member.getId(), hobby.getId())) {
            throw new BusinessException(CLUB_MEMBER_ALREADY_EXIST, "이미 존재하는 인원 : " + member.getId());
        }
        HobbyMember entity = HobbyMember.of(hobby, member, role);
        hobby.updateMemberCurrent();
        return hobbyMemberRepository.save(entity);
    }

    public void checkAReaders(Member member, Long hobbyId) {
        boolean check = member.getHobbyMemberList().stream()
                .anyMatch(hobbyMember -> hobbyMember.getHobby().getId() == hobbyId && hobbyMember.getRole() == ClubMemberRole.LEADER);
        if (!check) {
            throw new BusinessException(MEMBER_DO_NOT_HAVE_PERMISSION, "권한이 없습니다 : " + member.getId());
        }
    }
}
