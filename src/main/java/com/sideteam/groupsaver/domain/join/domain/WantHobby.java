package com.sideteam.groupsaver.domain.join.domain;

import com.sideteam.groupsaver.domain.category.domain.HobbyMajor;
import com.sideteam.groupsaver.domain.category.domain.HobbySub;
import com.sideteam.groupsaver.domain.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WantHobby {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private HobbyMajor hobbyCategory;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}