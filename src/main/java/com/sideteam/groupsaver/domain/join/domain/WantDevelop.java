package com.sideteam.groupsaver.domain.join.domain;

import com.sideteam.groupsaver.domain.join.enums.DevelopCategory;
import com.sideteam.groupsaver.domain.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WantDevelop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private DevelopCategory developCategory;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
