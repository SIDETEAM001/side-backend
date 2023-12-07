package com.sideteam.groupsaver.domain.club_schedule.domain;

import com.sideteam.groupsaver.domain.club_schedule.dto.ClubScheduleRequestDto;
import com.sideteam.groupsaver.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.Objects;

@SQLDelete(sql = "UPDATE ClubSchedule SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted = false")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class ClubSchedule extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private boolean isDeleted;

    @JoinColumn(nullable = false)
    private Club club;

    @Column(nullable = false)
    private LocalDateTime meetAt;

    @Column(nullable = false)
    private String title;

    // TODO 모임 일정 참석 최대 인원수?

    public static ClubSchedule of(Club club, ClubScheduleRequestDto clubScheduleRequestDto) {
        return new ClubSchedule(club, clubScheduleRequestDto.meetAt(), clubScheduleRequestDto.title());
    }

    public void update(LocalDateTime meetAt, String title) {
        this.meetAt = Objects.requireNonNullElse(meetAt, this.meetAt);
        this.title = Objects.requireNonNullElse(title, this.title);
    }


    private ClubSchedule(Club club, LocalDateTime meetAt, String title) {
        this.club = club;
        this.meetAt = meetAt;
        this.title = title;
    }

}
