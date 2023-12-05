package com.sideteam.groupsaver.domain.join.domain;

import com.sideteam.groupsaver.domain.join.enums.HobbyCategory;
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

    private HobbyCategory hobbyCategory;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}