package com.sideteam.groupsaver.domain.qna.domain;

import com.sideteam.groupsaver.domain.common.BaseEntity;
import com.sideteam.groupsaver.domain.common.BaseTimeEntity;
import com.sideteam.groupsaver.domain.member.domain.Member;
import com.sideteam.groupsaver.domain.qna.dto.request.QnaRequestDto;
import com.sideteam.groupsaver.domain.qna_reply.domain.QnaReply;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Qna extends BaseEntity {

    /* PK */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /* 질문 제목 */
    @Column(nullable = false)
    private String title;

    /* 질문 내용 */
    @Column(nullable = false, columnDefinition = "TEXT")
    private String body;

    @OneToMany(mappedBy = "qna", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<QnaReply> qnaReplyList;

    /* 생성자 */
    private Qna(String title, String body, List<QnaReply> qnaReplyList) {
        this.title = title;
        this.body = body;
        this.qnaReplyList = qnaReplyList;
    }

    // 수정 시 null처리
    public void qnaUpdate(QnaRequestDto qnaRequestDto) {
        this.title = Objects.requireNonNullElse(qnaRequestDto.getTitle(), this.title);
        this.body = Objects.requireNonNullElse(qnaRequestDto.getBody(), this.body);
    }



}
