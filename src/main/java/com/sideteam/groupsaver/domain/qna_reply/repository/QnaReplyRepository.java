package com.sideteam.groupsaver.domain.qna_reply.repository;

import com.sideteam.groupsaver.domain.qna.domain.Qna;
import com.sideteam.groupsaver.domain.qna_reply.domain.QnaReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface QnaReplyRepository extends JpaRepository<QnaReply, Long> {

    List<QnaReply> findByParentReply(QnaReply parentReply);

}
