package com.sideteam.groupsaver.domain.join.domain;

import com.sideteam.groupsaver.domain.member.domain.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HobbyBookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

//    @ManyToOne
//    @JoinColumn(name = "hobby_id")
//    private Hobby hobby;
}
