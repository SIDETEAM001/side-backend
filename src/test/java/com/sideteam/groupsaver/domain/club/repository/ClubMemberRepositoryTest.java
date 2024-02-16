package com.sideteam.groupsaver.domain.club.repository;

import com.sideteam.groupsaver.domain.club.domain.Club;
import com.sideteam.groupsaver.domain.club.domain.ClubMember;
import com.sideteam.groupsaver.domain.club.domain.ClubMemberRole;
import com.sideteam.groupsaver.domain.member.domain.Member;
import com.sideteam.groupsaver.domain.member.repository.MemberRepository;
import com.sideteam.groupsaver.utils.context.DataJpaTestcontainersTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.IntStream;

import static com.sideteam.groupsaver.utils.provider.ClubProvider.createClub;
import static com.sideteam.groupsaver.utils.provider.MemberProvider.createMember;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTestcontainersTest
class ClubMemberRepositoryTest {

    @Autowired
    private ClubMemberRepository clubMemberRepository;

    @Autowired
    private ClubRepository clubRepository;
    @Autowired
    private MemberRepository memberRepository;

    private final static int MEMBER_LIMIT = 5;

    private Club club;


    @BeforeEach
    void setup() {
        club = clubRepository.saveAndFlush(createClub(MEMBER_LIMIT));

        // 모임에 멤버 추가, 모임 최대 인원에서 -1 만큼 추가
        List<Member> members = memberRepository.saveAllAndFlush(IntStream.range(0, MEMBER_LIMIT - 1)
                .mapToObj(i -> createMember())
                .toList());

        clubMemberRepository.saveAllAndFlush(members.stream()
                .map(member -> ClubMember.of(club, member, ClubMemberRole.MEMBER))
                .toList());
    }


    @AfterEach
    void tearDown() {
        clubMemberRepository.deleteAll();
        memberRepository.deleteAll();
        clubRepository.deleteAll();
    }


    @Test
    @DisplayName("모임 인원이 가득 찼는지 확인")
    void hasExceededMemberLimit() {
        // given
        Member newMember = memberRepository.save(createMember());
        clubMemberRepository.save(ClubMember.of(club, newMember, ClubMemberRole.MEMBER));

        // when
        final boolean hasExceededMemberLimit = clubMemberRepository.hasExceededMemberLimit(club.getId());

        // then
        assertTrue(hasExceededMemberLimit);
    }

    @Test
    @DisplayName("해당 모임의 멤버가 역할이 있는지 확인")
    void hasClubRole() {
        // given
        Member newMember = memberRepository.save(createMember());
        clubMemberRepository.save(ClubMember.of(club, newMember, ClubMemberRole.MEMBER));

        // when
        final boolean hasClubRole = clubMemberRepository.hasClubRole(club.getId(), newMember.getId(), ClubMemberRole.MEMBER);

        // then
        assertTrue(hasClubRole);
    }

    @Test
    @DisplayName("해당 모임의 멤버가 역할이 없는지 확인")
    void givenMember_whenHasClubRole_thenFalse() {
        // given
        Member newMember = memberRepository.save(createMember());
        clubMemberRepository.save(ClubMember.of(club, newMember, ClubMemberRole.MEMBER));

        // when
        final boolean hasClubRole = clubMemberRepository.hasClubRole(club.getId(), newMember.getId(), ClubMemberRole.LEADER);

        // then
        assertFalse(hasClubRole);
    }

    @Test
    @DisplayName("해당 모임에 없는 멤버가 역할이 없는지 확인")
    void givenMemberNotInClub_whenHasClubRole_thenFalse() {
        // given
        Member newMember = memberRepository.save(createMember());

        // when
        final boolean hasClubRole = clubMemberRepository.hasClubRole(club.getId(), newMember.getId(), ClubMemberRole.LEADER);

        // then
        assertFalse(hasClubRole);
    }

}
