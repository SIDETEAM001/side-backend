package com.sideteam.groupsaver.domain.club_schedule.domain;

import com.sideteam.groupsaver.domain.club.domain.Club;
import com.sideteam.groupsaver.domain.club_schedule.dto.ClubScheduleRequestDto;
import com.sideteam.groupsaver.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    @ManyToOne(fetch = FetchType.LAZY)
    private Club club;

    @Column(nullable = false)
    private LocalDateTime meetAt;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Long maxParticipation;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private Long participationFee;


    public static ClubSchedule of(Club club, ClubScheduleRequestDto clubScheduleRequestDto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return new ClubSchedule(
                club,
                LocalDateTime.parse(clubScheduleRequestDto.meetAt(), formatter),
                clubScheduleRequestDto.title(),
                clubScheduleRequestDto.description(),
                clubScheduleRequestDto.maxParticipation(),
                clubScheduleRequestDto.location(),
                clubScheduleRequestDto.participationFee()
        );
    }

    public void update(LocalDateTime meetAt, String title) {
        this.meetAt = Objects.requireNonNullElse(meetAt, this.meetAt);
        this.title = Objects.requireNonNullElse(title, this.title);
    }

    private ClubSchedule(Club club, LocalDateTime meetAt, String title, String description,
                         Long maxParticipation, String location, Long participationFee) {
        this.club = club;
        this.meetAt = meetAt;
        this.title = title;
        this.description = description;
        this.maxParticipation = maxParticipation;
        this.location = location;
        this.participationFee = participationFee;
        this.isDeleted = false;
    }

}
