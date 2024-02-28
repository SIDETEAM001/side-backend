package com.sideteam.groupsaver.domain.qna.repository;

import com.sideteam.groupsaver.domain.qna.domain.Qna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface QnaRepository extends JpaRepository<Qna, Long> {

    /* 전체 조회 */
    @Query("SELECT q FROM Qna q JOIN FETCH q.creator")
    List<Qna> findAllQnaMember();

    /* 단건 조회 */
    @Query("SELECT q FROM Qna q JOIN FETCH q.creator WHERE q.id = :id")
    Optional<Qna> findQnaMemberById(Long id);


}
