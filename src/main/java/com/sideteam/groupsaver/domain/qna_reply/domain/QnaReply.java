package com.sideteam.groupsaver.domain.qna_reply.domain;

import com.sideteam.groupsaver.domain.common.BaseEntity;
import com.sideteam.groupsaver.domain.qna.domain.Qna;
import com.sideteam.groupsaver.domain.qna.dto.request.QnaRequestDto;
import com.sideteam.groupsaver.domain.qna_reply.dto.request.QnaReplyRequestDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class QnaReply extends BaseEntity {

    /* PK */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /* 댓글 내용 */
    @Column(nullable = false, columnDefinition = "TEXT")
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "qna_id")
    @OnDelete(action = OnDeleteAction.CASCADE) // QNA 게시글이 지워지면 댓글도 같이 삭제
    private Qna qna;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_reply_id")
    private QnaReply parentReply;

    @OneToMany(mappedBy = "parentReply", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QnaReply> childReply = new ArrayList<>();

    /* 생성자 */
    @Builder
    private QnaReply(String body, Qna qna, QnaReply parentReply) {
        this.body = body;
        this.qna = qna;
        this.parentReply = parentReply;
    }

    /* DTO -> Entity */
    public static QnaReply of(Qna qna, QnaReplyRequestDto qnaReplyRequestDto, QnaReply parentReply) {
        return QnaReply.builder()
                .body(qnaReplyRequestDto.getBody())
                .qna(qna)
                .parentReply(parentReply)
                .build();
    }

    // 수정 시 null처리
    public void qnaReplyUpdate(QnaReplyRequestDto qnaReplyRequestDto) {
        this.body = Objects.requireNonNullElse(qnaReplyRequestDto.getBody(), this.body);
    }

}