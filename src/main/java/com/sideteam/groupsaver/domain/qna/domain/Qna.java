package com.sideteam.groupsaver.domain.qna.domain;

import com.sideteam.groupsaver.domain.club.domain.Club;
import com.sideteam.groupsaver.domain.club.domain.ClubActivityType;
import com.sideteam.groupsaver.domain.club.domain.ClubType;
import com.sideteam.groupsaver.domain.common.BaseTimeEntity;
import com.sideteam.groupsaver.domain.qna.dto.request.QnaRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Qna extends BaseTimeEntity {

    /* PK */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /* 질문 제목 */
    private String title;

    /* 질문 내용 */
    private String body;

    /* 생성자 */

    private Qna(String title, String body) {
        this.title = title;
        this.body = body;
    }

    // 수정 시 null처리
    public void qnaUpdate(QnaRequestDto qnaRequestDto) {
        if (qnaRequestDto.getTitle() != null) {
            this.setTitle(qnaRequestDto.getTitle());
        }
        if (qnaRequestDto.getBody() != null) {
            this.setBody(qnaRequestDto.getBody());
        }
    }

}
