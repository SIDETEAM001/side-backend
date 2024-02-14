package com.sideteam.groupsaver.domain.member.repository;

import com.sideteam.groupsaver.config.IntegrationSpringBootTest;
import com.sideteam.groupsaver.domain.category.domain.ClubCategoryMajor;
import com.sideteam.groupsaver.domain.club.domain.Club;
import com.sideteam.groupsaver.domain.club.domain.ClubMember;
import com.sideteam.groupsaver.domain.club.domain.ClubMemberRole;
import com.sideteam.groupsaver.domain.club.repository.ClubMemberRepository;
import com.sideteam.groupsaver.domain.club.repository.ClubRepository;
import com.sideteam.groupsaver.domain.join.domain.ClubBookmark;
import com.sideteam.groupsaver.domain.join.domain.WantClubCategory;
import com.sideteam.groupsaver.domain.join.repository.ClubBookmarkRepository;
import com.sideteam.groupsaver.domain.join.repository.WantClubCategoryRepository;
import com.sideteam.groupsaver.domain.member.domain.Member;
import com.sideteam.groupsaver.domain.member.dto.response.MyProfileResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.sideteam.groupsaver.utils.provider.ClubProvider.createClub;
import static com.sideteam.groupsaver.utils.provider.MemberProvider.createMember;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
@IntegrationSpringBootTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private WantClubCategoryRepository wantClubCategoryRepository;
    @Autowired
    private ClubRepository clubRepository;
    @Autowired
    private ClubMemberRepository clubMemberRepository;
    @Autowired
    private ClubBookmarkRepository clubBookmarkRepository;

    private long memberId;
    private final List<ClubCategoryMajor> wantCategories = List.of(ClubCategoryMajor.BOOK, ClubCategoryMajor.CRAFT);


    @BeforeEach
    void setup() {
        log.debug("setup 시작");
        final Member member = memberRepository.save(createMember());
        memberId = member.getId();

        // 원하는 모임 카테고리 저장
        wantClubCategoryRepository.saveAll(wantCategories.stream()
                .map(category -> WantClubCategory.of(member, category)).toList());

        // 모임 생성
        final Club clubForJoin = clubRepository.save(createClub());
        // 모임에 멤버 추가
        clubMemberRepository.save(ClubMember.of(clubForJoin, member, ClubMemberRole.MEMBER));

        // 북마크할 모임 생성
        final List<Club> clubsForBookmark = clubRepository.saveAll(List.of(createClub(), createClub()));

        // 모임 북마크
        clubBookmarkRepository.saveAll(clubsForBookmark.stream()
                .map(club -> ClubBookmark.of(member, club)).toList());

        log.debug("setup 끝");
    }

    @Test
    void findMyProfileByMemberId() {
        MyProfileResponse myProfileResponse = memberRepository.findMyProfileByMemberId(memberId);

        assertThat(myProfileResponse.myClubCount()).isEqualTo(1);
        assertThat(myProfileResponse.clubCategories()).isEqualTo(wantCategories);
        assertThat(myProfileResponse.clubBookmarkCount()).isEqualTo(2);
    }

}
