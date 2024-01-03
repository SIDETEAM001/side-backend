package com.sideteam.groupsaver.domain.hobby.repository;

import com.sideteam.groupsaver.domain.hobby.domain.Hobby;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HobbyRepository extends JpaRepository<Hobby, Long> {
    @Modifying
    @Query("UPDATE Hobby h SET h.isStatus = false WHERE h.id = :hobbyId")
    void updateIsStatusByHobbyId(@Param("hobbyId") long hobbyId);
}
