package com.sideteam.groupsaver.domain.member.repository;

import com.sideteam.groupsaver.domain.category.domain.ClubCategory;
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
import com.sideteam.groupsaver.utils.context.DataJpaTestcontainersTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.sideteam.groupsaver.utils.provider.ClubProvider.createClub;
import static com.sideteam.groupsaver.utils.provider.MemberProvider.createMember;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
@DataJpaTestcontainersTest
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
    private final List<ClubCategoryMajor> wantCategories = List.of(
            ClubCategoryMajor.CHANGE, ClubCategoryMajor.BOOK, ClubCategoryMajor.CRAFT, ClubCategoryMajor.ETC_HOBBY);


    @BeforeEach
    void setup() {
        log.debug("setup 시작");
        final Member member = memberRepository.save(createMember());
        memberId = member.getId();

        // 원하는 모임 카테고리 저장
        wantClubCategoryRepository.saveAllAndFlush(wantCategories.stream()
                .map(category -> WantClubCategory.of(member, category)).toList());

        // 모임 생성
        final Club clubForJoin = clubRepository.save(createClub());
        // 모임에 멤버 추가
        clubMemberRepository.saveAndFlush(ClubMember.of(clubForJoin, member, ClubMemberRole.MEMBER));

        // 북마크할 모임 생성
        final List<Club> clubsForBookmark = clubRepository.saveAll(List.of(createClub(), createClub()));

        // 모임 북마크
        clubBookmarkRepository.saveAllAndFlush(clubsForBookmark.stream()
                .map(club -> ClubBookmark.of(member, club)).toList());

        log.debug("setup 끝");
    }

    @AfterEach
    void tearDown() {
        memberRepository.deleteAll();
        wantClubCategoryRepository.deleteAll();
        clubRepository.deleteAll();
        clubMemberRepository.deleteAll();
        clubBookmarkRepository.deleteAll();
    }


    @Test
    @DisplayName("내 프로필 조회 성공 - 회원 ID로 조회")
    void findMyProfileByMemberId() {
        // given
        final List<ClubCategoryMajor> expectedDevelops = wantCategories.stream()
                .filter(category -> category.getCategory().equals(ClubCategory.DEVELOP)).toList();

        final List<ClubCategoryMajor> expectedHobbies = wantCategories.stream()
                .filter(category -> category.getCategory().equals(ClubCategory.HOBBY)).toList();

        // when
        MyProfileResponse myProfileResponse = memberRepository.findMyProfileByMemberId(memberId);

        // then
        assertThat(myProfileResponse.myClubCount()).isEqualTo(1);
        assertThat(myProfileResponse.develops()).isEqualTo(expectedDevelops);
        assertThat(myProfileResponse.hobbies()).isEqualTo(expectedHobbies);
        assertThat(myProfileResponse.clubBookmarkCount()).isEqualTo(2);
    }

}
