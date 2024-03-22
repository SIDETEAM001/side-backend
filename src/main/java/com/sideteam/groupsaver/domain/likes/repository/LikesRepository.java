package com.sideteam.groupsaver.domain.likes.repository;

import com.sideteam.groupsaver.domain.likes.domain.Likes;
import com.sideteam.groupsaver.domain.member.domain.Member;
import com.sideteam.groupsaver.domain.qna.domain.Qna;
import com.sideteam.groupsaver.domain.qna_reply.domain.QnaReply;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LikesRepository  extends JpaRepository<Likes, Long> {

    /* 좋아요 여부 확인 */
    boolean existsByMemberIdAndQnaId(Long memberId, Long qnaId);

}