package com.sideteam.groupsaver.domain.likes.domain;

import com.sideteam.groupsaver.domain.common.BaseEntity;
import com.sideteam.groupsaver.domain.common.BaseTimeEntity;
import com.sideteam.groupsaver.domain.likes.dto.request.LikesRequestDto;
import com.sideteam.groupsaver.domain.member.domain.Member;
import com.sideteam.groupsaver.domain.qna.domain.Qna;
import com.sideteam.groupsaver.domain.qna_reply.domain.QnaReply;
import com.sideteam.groupsaver.domain.qna_reply.dto.request.QnaReplyRequestDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
public class Likes extends BaseTimeEntity {

    /* PK */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qna_id")
    private Qna qna;

    /* 생성자 */
    @Builder
    private Likes(Member member, Qna qna) {
        this.member = member;
        this.qna = qna;
    }

    /* DTO -> Entity */
    public static Likes of(Member member, Qna qna) {
        return Likes.builder()
                .member(member)
                .qna(qna)
                .build();
    }

}
