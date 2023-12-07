package com.sideteam.groupsaver.domain.club_schedule.repository;

import com.sideteam.groupsaver.domain.club_schedule.domain.ClubSchedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface ClubScheduleRepository extends JpaRepository<ClubSchedule, Long> {

    @Query("SELECT cs FROM ClubSchedule cs WHERE cs.club.id=:clubId")
    Page<ClubSchedule> findAllByClubId(Long clubId, Pageable pageable);

}
