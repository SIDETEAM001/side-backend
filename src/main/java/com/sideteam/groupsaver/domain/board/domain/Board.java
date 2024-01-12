package com.sideteam.groupsaver.domain.board.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Entity
@Table(name = "Board")
@SequenceGenerator(name = "tnGenerator", sequenceName = "B_SEQ", allocationSize = 1)
public class Board {


        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tnGenerator")
        @Column(name = "B_SEQ")
        private long seq;

        @Column(name = "TITLE", length = 200)
        private String title;

        @Column(name = "CONTENT", length = 500)
        private String content;

        @Column(name = "REG_ID", length = 30, columnDefinition = "")
        private String regId;

        @Column(name = "CREATED_TIME", columnDefinition = "")
        private LocalDateTime createdDate;

        @Column(name = "UPDATED_TIME")
        private LocalDateTime updatedDate;

        // orphanRemoval : PK(JoinColumn)값이 NULL로 변한 자식은 고아객체라고 하여 연결된 점이 없는 객체를 삭제해주는 옵션
        public String getCreatedDate() {
            return createdDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }

        public String getUpdatedDate() {
            return updatedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }

        @Builder
        public Board(Long id, String title, String content, LocalDateTime createdDate, LocalDateTime updatedDate, String redId) {
                this.seq = seq;
                this.title = title;
                this.content = content;
                this.createdDate = createdDate;
                this.updatedDate = updatedDate;
                this.regId = regId;
        }
}
