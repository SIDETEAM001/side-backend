package com.sideteam.groupsaver.domain.club_schedule.service;

import com.sideteam.groupsaver.domain.club_schedule.domain.ClubSchedule;
import com.sideteam.groupsaver.domain.club_schedule.domain.ClubScheduleMember;
import com.sideteam.groupsaver.domain.club_schedule.dto.ClubScheduleMemberDto;
import com.sideteam.groupsaver.domain.club_schedule.repository.ClubScheduleMemberRepository;
import com.sideteam.groupsaver.domain.club_schedule.repository.ClubScheduleRepository;
import com.sideteam.groupsaver.domain.member.domain.Member;
import com.sideteam.groupsaver.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Slf4j
@Service
public class ClubScheduleMemberService {

    private final ClubScheduleRepository clubScheduleRepository;
    private final ClubScheduleMemberRepository clubScheduleMemberRepository;

    private final MemberRepository memberRepository;

    public Page<ClubScheduleMemberDto> getClubScheduleMembers(Long clubScheduleId, Pageable pageable) {
        Page<ClubScheduleMember> clubScheduleMembers = clubScheduleMemberRepository.findAllByClubScheduleId(clubScheduleId, pageable);

        List<ClubScheduleMemberDto> clubScheduleResponses = clubScheduleMembers.getContent().stream()
                .map(ClubScheduleMemberDto::from)
                .toList();
        return new PageImpl<>(clubScheduleResponses, clubScheduleMembers.getPageable(), clubScheduleMembers.getTotalElements());
    }

    @PreAuthorize("isAuthenticated() and (( #memberId.toString() == principal.username ) or hasRole('ROLE_ADMIN'))")
    public void joinSchedule(Long clubScheduleId, Long memberId) {
        ClubSchedule clubSchedule = findClubScheduleOrThrow(clubScheduleId);
        Member member = findMemberOrThrow(memberId);
        // TODO 모임 일정 참석 최대 인원수 제한이 필요하다면 로직 추가
        clubScheduleMemberRepository.save(ClubScheduleMember.of(clubSchedule, member));
    }

    @PreAuthorize("isAuthenticated() and (( #memberId.toString() == principal.username ) or hasRole('ROLE_ADMIN'))")
    public void leaveSchedule(Long clubScheduleId, Long memberId) {
        clubScheduleMemberRepository.deleteByClubScheduleIdAndMemberId(clubScheduleId, memberId);
    }


    private ClubSchedule findClubScheduleOrThrow(Long clubScheduleId) {
        return clubScheduleRepository.getReferenceById(clubScheduleId);
    }

    private Member findMemberOrThrow(Long memberId) {
        return memberRepository.getReferenceById(memberId);
    }

}
