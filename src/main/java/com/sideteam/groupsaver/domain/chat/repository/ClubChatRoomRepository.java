package com.sideteam.groupsaver.domain.chat.repository;

import com.sideteam.groupsaver.domain.chat.domain.ClubChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubChatRoomRepository extends JpaRepository<ClubChatRoom, Long> {
}
