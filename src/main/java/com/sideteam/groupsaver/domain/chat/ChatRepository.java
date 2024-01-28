package com.sideteam.groupsaver.domain.chat;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Query("SELECT ch FROM Chat ch JOIN FETCH Member m ON ch.sender = m WHERE ch.club.id=:clubId")
    Page<Chat> findAllByClubId(Long clubId, Pageable pageable);

}
