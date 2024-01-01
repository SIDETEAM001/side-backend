package com.sideteam.groupsaver.domain.club.service;

import com.sideteam.groupsaver.domain.club.domain.Club;
import com.sideteam.groupsaver.domain.club.domain.ClubMember;
import com.sideteam.groupsaver.domain.club.domain.ClubMemberRole;
import com.sideteam.groupsaver.domain.club.gateway.ClubGateway;
import com.sideteam.groupsaver.domain.club.repository.ClubMemberRepository;
import com.sideteam.groupsaver.domain.member.domain.Member;
import com.sideteam.groupsaver.global.auth.userdetails.GetAuthUserUtils;
import com.sideteam.groupsaver.global.exception.BusinessException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.sideteam.groupsaver.global.exception.clubA.ClubErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional
public class ClubMemberService {
    private final ClubMemberRepository clubMemberRepository;
    private final ClubGateway gateway;

    public void createClubMember(Club club, Member member, ClubMemberRole role) {
        if (clubMemberRepository.existsByMemberId(member.getId())) {
            throw new BusinessException(CLUB_MEMBER_ALREADY_EXIST, "멤버 아이디 : " + member.getId());
        }
        ClubMember entity = ClubMember.of(club, member, role);
        club.updateMemberCurrent();
        clubMemberRepository.save(entity);
    }

    public void checkAReaders(Long memberId, Long ClubId) {
        ClubMember clubMember = clubMemberRepository.findByMemberIdAndClubId(memberId, clubMemberRepository);
        if (clubMember.getRole() != ClubMemberRole.LEADER) {
            throw new BusinessException(MEMBER_DO_NOT_HAVE_PERMISSION, "권한이 없습니다 : " + memberId);
        }
    }
}