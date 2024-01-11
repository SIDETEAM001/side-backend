package com.sideteam.groupsaver.domain.qna.repository;

import com.sideteam.groupsaver.domain.qna.domain.Qna;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QnaRepository extends JpaRepository<Qna, Long> {
}
