package com.sideteam.groupsaver.domain.mypage.service;

import com.sideteam.groupsaver.domain.category.domain.DevelopMajor;
import com.sideteam.groupsaver.domain.category.domain.HobbyMajor;
import com.sideteam.groupsaver.domain.category.domain.HobbySub;
import com.sideteam.groupsaver.domain.club.domain.Club;
import com.sideteam.groupsaver.domain.club.domain.ClubMember;
import com.sideteam.groupsaver.domain.club.domain.ClubMemberStatus;
import com.sideteam.groupsaver.domain.club.repository.ClubMemberRepository;
import com.sideteam.groupsaver.domain.club.repository.ClubRepository;
import com.sideteam.groupsaver.domain.join.domain.ClubBookmark;
import com.sideteam.groupsaver.domain.join.domain.WantDevelop;
import com.sideteam.groupsaver.domain.join.domain.WantHobby;
import com.sideteam.groupsaver.domain.join.repository.ClubBookmarkRepository;
import com.sideteam.groupsaver.domain.join.repository.WantDevelopRepository;
import com.sideteam.groupsaver.domain.join.repository.WantHobbyRepository;
import com.sideteam.groupsaver.domain.member.domain.Member;
import com.sideteam.groupsaver.domain.member.repository.MemberRepository;
import com.sideteam.groupsaver.domain.mypage.dto.request.MyInfoUpdateRequest;
import com.sideteam.groupsaver.domain.mypage.dto.response.ClubFindResponse;
import com.sideteam.groupsaver.domain.mypage.dto.response.MyInfoFindResponse;
import com.sideteam.groupsaver.global.exception.auth.AuthErrorCode;
import com.sideteam.groupsaver.global.exception.auth.AuthErrorException;
import com.sideteam.groupsaver.global.exception.club.ClubErrorCode;
import com.sideteam.groupsaver.global.exception.club.ClubErrorException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class MyPageService {
    private final ClubRepository clubRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final MemberRepository memberRepository;
    private final WantDevelopRepository wantDevelopRepository;
    private final WantHobbyRepository wantHobbyRepository;
    private final ClubBookmarkRepository clubBookmarkRepository;


    @Transactional
    public MyInfoFindResponse findMyInfo(Long id) {
        Member member = findMember(id);

        List<WantDevelop> wantDevelopList = member.getWantDevelopList();
        List<WantHobby> wantHobbyList = member.getWantHobbyList();

        List<DevelopMajor> developCategoryList = wantDevelopList.stream()
                .map(WantDevelop::getDevelopCategory)
                .collect(Collectors.toList());

        List<HobbyMajor> hobbyCategoryList = wantHobbyList.stream()
                .map(WantHobby::getHobbyCategory)
                .collect(Collectors.toList());

        return MyInfoFindResponse.builder()
                .password(member.getPassword())
                .phoneNumber(member.getPhoneNumber())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .developCategoryList(developCategoryList)
                .hobbyCategoryList(hobbyCategoryList)
                .build();
    }

    @Transactional
    public MyInfoFindResponse updateMyInfo(MyInfoUpdateRequest dto, Long id) {
        Member member = findMember(id);
        if(dto.getNickname() != null)
            member.setNickname(dto.getNickname());

        if(dto.getBirth() != null)
            member.setBirth(dto.getBirth());

        if(dto.getUrl() != null)
            member.setProfileUrl(dto.getUrl());

        if(dto.getJobCategory() != null)
            member.setJobCategory(dto.getJobCategory());

        for(HobbyMajor hobbyCategory : dto.getHobbyCategory()){
            WantHobby wantHobby = WantHobby.builder()
                    .hobbyCategory(hobbyCategory)
                    .member(member)
                    .build();
            wantHobbyRepository.save(wantHobby);
        }

        for(DevelopMajor developCategory : dto.getDevelopCategory()){
            WantDevelop wantDevelop = WantDevelop.builder()
                    .developCategory(developCategory)
                    .member(member)
                    .build();
            wantDevelopRepository.save(wantDevelop);
        }
        return MyInfoFindResponse.toDto(member);
    }

    @Transactional
    public Page<ClubFindResponse> findMyClub(Boolean star, Long userId, Pageable pageable) {
        Member member = findMember(userId);

        Page<Club> result;
        // 찜한모임 - star만 해두고 로그인 사용자는 멤버가 아닐 수 있음
        if(star){
            Page<ClubBookmark> clubPage = clubBookmarkRepository.findByMember(member, pageable);

            List<Club> clubs = clubPage.getContent().stream()
                    .map(ClubBookmark::getClub)
                    .collect(Collectors.toList());

            result = new PageImpl<>(clubs, clubPage.getPageable(), clubPage.getTotalElements());
        }else{
            // 내 모임
            result = clubMemberRepository.findByMemberAndStatus(member, ClubMemberStatus.ACTIVITY, pageable)
                    .map(ClubMember::getClub);
        }

        List<ClubFindResponse> clubResponses = result.getContent().stream()
                .map(ClubFindResponse::toDto)
                .collect(Collectors.toList());

        return new PageImpl<>(clubResponses, result.getPageable(), result.getTotalElements());
    }


    private Member findMember(Long id){
        return memberRepository.findById(id).orElseThrow(()
                -> new AuthErrorException(AuthErrorCode.FAILED_AUTHENTICATION, "로그인한 멤버정보를 가져올 수 없습니다."));
    }

    private Club findClub(Long id){
        return clubRepository.findById(id).orElseThrow(()
                -> new ClubErrorException(ClubErrorCode.NOT_FOUND_CLUB, "모임을 찾을 수 없습니다."));
    }
}
