package com.sideteam.groupsaver.domain.club_schedule.repository;

import com.sideteam.groupsaver.domain.club_schedule.domain.ClubScheduleMember;
import com.sideteam.groupsaver.domain.member.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface ClubScheduleMemberRepository extends JpaRepository<ClubScheduleMember, Long> {

    @Query("SELECT csm.member FROM ClubScheduleMember csm WHERE csm.clubSchedule.id = :clubScheduleId")
    Page<Member> findAllScheduleMembersByClubScheduleId(Long clubScheduleId, Pageable pageable);

    void deleteByClubScheduleIdAndMemberId(Long clubScheduleId, Long memberId);
}
