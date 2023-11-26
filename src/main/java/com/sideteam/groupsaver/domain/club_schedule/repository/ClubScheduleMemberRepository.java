package com.sideteam.groupsaver.domain.club_schedule.repository;

import com.sideteam.groupsaver.domain.club_schedule.domain.ClubScheduleMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubScheduleMemberRepository extends JpaRepository<ClubScheduleMember, Long> {

    void deleteByClubScheduleIdAndMemberId(Long clubScheduleId, Long memberId);
}
