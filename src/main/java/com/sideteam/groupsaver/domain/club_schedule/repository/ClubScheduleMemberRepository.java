package com.sideteam.groupsaver.domain.club_schedule.repository;

import com.sideteam.groupsaver.domain.club_schedule.domain.ClubScheduleMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ClubScheduleMemberRepository extends JpaRepository<ClubScheduleMember, Long> {

    Page<ClubScheduleMember> findAllByClubScheduleId(Long clubScheduleId, Pageable pageable);
    void deleteByClubScheduleIdAndMemberId(Long clubScheduleId, Long memberId);
}
