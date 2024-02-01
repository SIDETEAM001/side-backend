package com.sideteam.groupsaver.domain.club.repository;

import com.sideteam.groupsaver.domain.club.domain.Club;
import com.sideteam.groupsaver.global.exception.BusinessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

import static com.sideteam.groupsaver.global.exception.club.ClubErrorCode.CLUB_NOT_FOUND;

public interface ClubRepository extends JpaRepository<Club, Long>, ClubRepositoryCustom {
    @Modifying
    @Query("UPDATE Club c SET c.isActive = false WHERE c.id = :clubId")
    void deactivateClub(@Param("clubId") Long clubId);

    default Club findByIdOrThrow(Long id) {
        return findById(id).orElseThrow(() ->
                new BusinessException(CLUB_NOT_FOUND, "잘못된 모임 아이디 : " + id));
    }

    Optional<Club> findByIdAndIsActiveTrue(Long id);

}
