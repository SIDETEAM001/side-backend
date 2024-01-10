package com.sideteam.groupsaver.domain.chat.repository;

import com.sideteam.groupsaver.domain.chat.domain.ChatMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMemberRepository extends JpaRepository<ChatMember, Long> {
}
