package com.sideteam.groupsaver.domain.club_schedule.repository;

import com.sideteam.groupsaver.domain.club_schedule.domain.ClubScheduleMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface ClubScheduleMemberRepository extends JpaRepository<ClubScheduleMember, Long> {

    List<ClubScheduleMember> findAll(Long clubScheduleId, Pageable pageable);
    void deleteByClubScheduleIdAndMemberId(Long clubScheduleId, Long memberId);
}
