package com.sideteam.groupsaver.domain.member.service;

import com.sideteam.groupsaver.domain.join.domain.WantClubCategory;
import com.sideteam.groupsaver.domain.join.repository.WantClubCategoryRepository;
import com.sideteam.groupsaver.domain.member.domain.Member;
import com.sideteam.groupsaver.domain.member.dto.request.MemberProfileUpdateRequest;
import com.sideteam.groupsaver.domain.member.dto.response.MemberProfileResponse;
import com.sideteam.groupsaver.domain.member.dto.response.MyProfileResponse;
import com.sideteam.groupsaver.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberProfileService {

    private final MemberRepository memberRepository;
    private final WantClubCategoryRepository wantClubCategoryRepository;

    @PreAuthorize("@authorityChecker.hasAuthority(#memberId)")
    public MyProfileResponse getMyProfile(Long memberId) {
        return memberRepository.findMyProfileByMemberId(memberId);
    }

    public MemberProfileResponse getMemberProfile(Long memberId) {
        Member member = memberRepository.findByIdOrThrow(memberId);
        return MemberProfileResponse.of(member);
    }

    @Transactional
    @PreAuthorize("@authorityChecker.hasAuthority(#memberId)")
    public MyProfileResponse updateMemberProfile(Long memberId, MemberProfileUpdateRequest updateRequest) {
        Member member = memberRepository.findByIdOrThrow(memberId);
        wantClubCategoryRepository.deleteAllByMemberId(memberId);

        List<WantClubCategory> wantClubCategories = wantClubCategoryRepository.saveAll(updateRequest.clubCategories().stream()
                .map(category -> WantClubCategory.of(member, category))
                .toList());
        member.update(updateRequest);
        member.updateWantClubCategory(wantClubCategories);
        return memberRepository.findMyProfileByMemberId(memberId);
    }

}
