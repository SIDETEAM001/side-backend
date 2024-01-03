package com.sideteam.groupsaver.domain.club_schedule.domain;

import com.sideteam.groupsaver.domain.common.BaseTimeEntity;
import com.sideteam.groupsaver.domain.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_schedule_member_constraint",
                        columnNames = {"club_schedule_id", "member_id"}
                )
        }
)
@Entity
public class ClubScheduleMember extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(nullable = false, name = "club_schedule_id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private ClubSchedule clubSchedule;

    @JoinColumn(nullable = false, name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Member member;


    public static ClubScheduleMember of(ClubSchedule clubSchedule, Member member) {
        return new ClubScheduleMember(clubSchedule, member);
    }


    private ClubScheduleMember(ClubSchedule clubSchedule, Member member) {
        this.clubSchedule = clubSchedule;
        this.member = member;
    }

}
