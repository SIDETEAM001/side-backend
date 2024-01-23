package com.sideteam.groupsaver.domain.mypage.service;

import com.sideteam.groupsaver.domain.club.domain.Club;
import com.sideteam.groupsaver.domain.club.domain.ClubMember;
import com.sideteam.groupsaver.domain.club.domain.ClubMemberStatus;
import com.sideteam.groupsaver.domain.club.repository.ClubMemberRepository;
import com.sideteam.groupsaver.domain.join.domain.ClubBookmark;
import com.sideteam.groupsaver.domain.join.domain.WantClubCategory;
import com.sideteam.groupsaver.domain.join.repository.ClubBookmarkRepository;
import com.sideteam.groupsaver.domain.join.repository.WantClubCategoryRepository;
import com.sideteam.groupsaver.domain.member.domain.Member;
import com.sideteam.groupsaver.domain.member.repository.MemberRepository;
import com.sideteam.groupsaver.domain.mypage.dto.request.MyInfoUpdateRequest;
import com.sideteam.groupsaver.domain.mypage.dto.response.ClubFindResponse;
import com.sideteam.groupsaver.domain.mypage.dto.response.MyInfoFindResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class MyPageService {
    private final ClubMemberRepository clubMemberRepository;
    private final MemberRepository memberRepository;
    private final ClubBookmarkRepository clubBookmarkRepository;
    private final WantClubCategoryRepository wantClubCategoryRepository;

    @Transactional(readOnly = true)
    public MyInfoFindResponse findMyInfo(Long memberId) {
        Member member = memberRepository.findByIdOrThrow(memberId);

        return MyInfoFindResponse.builder()
                .phoneNumber(member.getPhoneNumber())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .clubCategories(member.getWantClubCategories().stream()
                        .map(WantClubCategory::getCategoryMajor).toList())
                .build();
    }

    @Transactional
    public MyInfoFindResponse updateMyInfo(MyInfoUpdateRequest dto, Long memberId) {
        Member member = memberRepository.findByIdOrThrow(memberId);

        member.update(dto);
        List<WantClubCategory> wantClubCategories = dto.getClubCategories().stream()
                .map(category -> WantClubCategory.of(member, category)).toList();

        wantClubCategoryRepository.saveAll(wantClubCategories);

        return MyInfoFindResponse.toDto(member);
    }

    @Transactional(readOnly = true)
    public Page<ClubFindResponse> findMyClub(Boolean star, Long memberId, Pageable pageable) {
        Member member = memberRepository.findByIdOrThrow(memberId);

        Page<Club> result;
        // 찜한모임 - star만 해두고 로그인 사용자는 멤버가 아닐 수 있음
        if (star) {
            Page<ClubBookmark> clubPage = clubBookmarkRepository.findByMember(member, pageable);

            List<Club> clubs = clubPage.getContent().stream()
                    .map(ClubBookmark::getClub)
                    .toList();

            result = new PageImpl<>(clubs, clubPage.getPageable(), clubPage.getTotalElements());
        } else {
            // 내 모임
            result = clubMemberRepository.findByMemberAndStatus(member, ClubMemberStatus.ACTIVITY, pageable)
                    .map(ClubMember::getClub);
        }

        List<ClubFindResponse> clubResponses = result.getContent().stream()
                .map(ClubFindResponse::toDto)
                .toList();

        return new PageImpl<>(clubResponses, result.getPageable(), result.getTotalElements());
    }

}
