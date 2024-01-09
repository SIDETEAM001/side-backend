package com.sideteam.groupsaver.domain.club.service;

import com.sideteam.groupsaver.domain.club.domain.Club;
import com.sideteam.groupsaver.domain.club.domain.ClubMember;
import com.sideteam.groupsaver.domain.club.domain.ClubMemberRole;
import com.sideteam.groupsaver.domain.club.repository.ClubMemberRepository;
import com.sideteam.groupsaver.domain.member.domain.Member;
import com.sideteam.groupsaver.global.exception.BusinessException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.sideteam.groupsaver.global.exception.club.ClubErrorCode.CLUB_MEMBER_ALREADY_EXIST;
import static com.sideteam.groupsaver.global.exception.club.ClubScheduleErrorCode.MEMBER_DO_NOT_HAVE_PERMISSION;

@Service
@RequiredArgsConstructor
@Transactional
public class ClubMemberService {
    private final ClubMemberRepository clubMemberRepository;

    public ClubMember createClubMember(Club club, Member member, ClubMemberRole role) {
        if (clubMemberRepository.existsByMemberIdAndClubId(member.getId(),club.getId())) {
            throw new BusinessException(CLUB_MEMBER_ALREADY_EXIST, "이미 존재하는 인원 : " + member.getId());
        }
        ClubMember entity = ClubMember.of(club, member, role);
        club.updateMemberCurrent();
        return clubMemberRepository.save(entity);
    }

    public void checkAReaders(Long memberId, Long clubId) {
        ClubMember clubMember = clubMemberRepository.findByMemberIdAndClubId(memberId, clubId);
        if (clubMember.getRole() != ClubMemberRole.LEADER) {
            throw new BusinessException(MEMBER_DO_NOT_HAVE_PERMISSION, "권한이 없습니다 : " + memberId);
        }
    }
}