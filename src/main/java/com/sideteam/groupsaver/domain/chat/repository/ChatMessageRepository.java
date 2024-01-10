package com.sideteam.groupsaver.domain.chat.repository;

import com.sideteam.groupsaver.domain.chat.domain.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
}
