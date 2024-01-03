package com.sideteam.groupsaver.domain.club_schedule.repository;

import com.sideteam.groupsaver.domain.club_schedule.domain.ClubSchedule;
import com.sideteam.groupsaver.global.exception.BusinessException;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

import static com.sideteam.groupsaver.global.exception.club.ClubScheduleErrorCode.CLUB_SCHEDULE_NOT_FOUND;


public interface ClubScheduleRepository extends JpaRepository<ClubSchedule, Long> {

    default ClubSchedule findOrThrowWithReadLock(Long clubScheduleId) {
        return findByIdForReadLock(clubScheduleId)
                .orElseThrow(() -> new BusinessException(CLUB_SCHEDULE_NOT_FOUND, clubScheduleId.toString()));
    }

    @Query("SELECT cs FROM ClubSchedule cs WHERE cs.club.id=:clubId")
    Page<ClubSchedule> findAllByClubId(Long clubId, Pageable pageable);

    @Lock(value = LockModeType.PESSIMISTIC_READ)
    @Query("select cs from ClubSchedule cs where cs.id=:id")
    Optional<ClubSchedule> findByIdForReadLock(Long id);

}
