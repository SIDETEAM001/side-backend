package com.sideteam.groupsaver.domain.club_schedule.service;

import com.sideteam.groupsaver.domain.club.repository.ClubMemberRepository;
import com.sideteam.groupsaver.domain.club_schedule.domain.ClubSchedule;
import com.sideteam.groupsaver.domain.club_schedule.domain.ClubScheduleMember;
import com.sideteam.groupsaver.domain.club_schedule.dto.response.ClubScheduleMemberResponse;
import com.sideteam.groupsaver.domain.club_schedule.repository.ClubScheduleMemberRepository;
import com.sideteam.groupsaver.domain.club_schedule.repository.ClubScheduleRepository;
import com.sideteam.groupsaver.domain.member.domain.Member;
import com.sideteam.groupsaver.domain.member.repository.MemberRepository;
import com.sideteam.groupsaver.global.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.sideteam.groupsaver.global.exception.club.ClubScheduleErrorCode.CLUB_SCHEDULE_IS_FULL;

@RequiredArgsConstructor
@Transactional
@Slf4j
@Service
public class ClubScheduleMemberService {

    private final ClubScheduleRepository clubScheduleRepository;
    private final ClubScheduleMemberRepository clubScheduleMemberRepository;

    private final ClubMemberRepository clubMemberRepository;

    private final MemberRepository memberRepository;


    @Transactional(readOnly = true)
    public Page<ClubScheduleMemberResponse> getClubScheduleMembers(Long clubScheduleId, Pageable pageable) {
        Page<ClubScheduleMember> clubScheduleMembers = clubScheduleMemberRepository.findAllByClubScheduleId(clubScheduleId, pageable);

        List<ClubScheduleMemberResponse> clubScheduleResponses = clubScheduleMembers.getContent().stream()
                .map(ClubScheduleMemberResponse::from)
                .toList();
        return new PageImpl<>(clubScheduleResponses, clubScheduleMembers.getPageable(), clubScheduleMembers.getTotalElements());
    }

    @PreAuthorize("isAuthenticated() AND (( #memberId.toString() == principal.username ) OR hasRole('ROLE_ADMIN'))")
    public void joinSchedule(Long clubScheduleId, Long memberId) {
        ClubSchedule clubSchedule = clubScheduleRepository.findOrThrowWithReadLock(clubScheduleId);

        clubMemberRepository.throwIfMemberNotInClub(memberId, clubSchedule.getClub().getId());
        checkIfParticipationExceed(clubSchedule);

        Member member = memberRepository.getReferenceById(memberId);

        clubScheduleMemberRepository.save(ClubScheduleMember.of(clubSchedule, member));
    }

    @PreAuthorize("isAuthenticated() AND (( #memberId.toString() == principal.username ) OR hasRole('ROLE_ADMIN'))")
    public void leaveSchedule(Long clubScheduleId, Long memberId) {
        clubScheduleMemberRepository.deleteByClubScheduleIdAndMemberId(clubScheduleId, memberId);
    }


    private void checkIfParticipationExceed(ClubSchedule clubSchedule) {
        if (clubSchedule.isReachMaximumParticipation()) {
            log.warn("이미 가득찬 모임 일정! 모임일정 ID: {}", clubSchedule.getId());
            throw new BusinessException(CLUB_SCHEDULE_IS_FULL, clubSchedule.getId().toString());
        }
    }

}
