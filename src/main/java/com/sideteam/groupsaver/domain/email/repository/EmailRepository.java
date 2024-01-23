package com.sideteam.groupsaver.domain.email.repository;

import com.sideteam.groupsaver.domain.email.domain.EmailMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<EmailMessage, Long> {
}
