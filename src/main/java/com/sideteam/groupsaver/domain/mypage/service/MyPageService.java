package com.sideteam.groupsaver.domain.mypage.service;

import com.sideteam.groupsaver.domain.join.domain.ClubBookmark;
import com.sideteam.groupsaver.domain.join.domain.WantDevelop;
import com.sideteam.groupsaver.domain.join.domain.WantHobby;
import com.sideteam.groupsaver.domain.join.enums.DevelopCategory;
import com.sideteam.groupsaver.domain.join.enums.HobbyCategory;
import com.sideteam.groupsaver.domain.join.repository.ClubBookmarkRepository;
import com.sideteam.groupsaver.domain.join.repository.HobbyBookmarkRepository;
import com.sideteam.groupsaver.domain.join.repository.WantDevelopRepository;
import com.sideteam.groupsaver.domain.join.repository.WantHobbyRepository;
import com.sideteam.groupsaver.domain.member.domain.Member;
import com.sideteam.groupsaver.domain.member.repository.MemberRepository;
import com.sideteam.groupsaver.domain.mypage.domain.Club;
import com.sideteam.groupsaver.domain.mypage.dto.request.MyInfoUpdateRequest;
import com.sideteam.groupsaver.domain.mypage.dto.response.ClubFindResponse;
import com.sideteam.groupsaver.domain.mypage.dto.response.MyInfoFindResponse;
import com.sideteam.groupsaver.domain.mypage.repository.ClubRepository;
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
    private final MemberRepository memberRepository;
    private final WantDevelopRepository wantDevelopRepository;
    private final WantHobbyRepository wantHobbyRepository;
    private final ClubBookmarkRepository clubBookmarkRepository;
    private final HobbyBookmarkRepository hobbyBookmarkRepository;


    @Transactional
    public MyInfoFindResponse findMyInfo(Long id) {
        Member member = findMember(id);
        return MyInfoFindResponse.toDto(member);
    }

    @Transactional
    public MyInfoFindResponse updateMyInfo(MyInfoUpdateRequest dto, Long id) {
        Member member = findMember(id);
        if(dto.getNickname() != null)
            member.setNickname(dto.getNickname());

        if(dto.getJobCategory() != null)
            member.setJobCategory(dto.getJobCategory());

        for(HobbyCategory hobbyCategory : dto.getHobbyCategory()){
            WantHobby wantHobby = WantHobby.builder()
                    .hobbyCategory(hobbyCategory)
                    .member(member)
                    .build();
            wantHobbyRepository.save(wantHobby);
        }

        for(DevelopCategory developCategory : dto.getDevelopCategory()){
            WantDevelop wantDevelop = WantDevelop.builder()
                    .developCategory(developCategory)
                    .member(member)
                    .build();
            wantDevelopRepository.save(wantDevelop);
        }

        return MyInfoFindResponse.toDto(member);
    }

    @Transactional
    public Page<ClubFindResponse> findMyClub(Boolean star, Long id, Pageable pageable) {
        Member member = findMember(id);

        Page<Club> result;
        if(star){
            Page<ClubBookmark> clubPage = clubBookmarkRepository.findByMember(member, pageable);

            List<Club> clubs = clubPage.getContent().stream()
                    .map(ClubBookmark::getClub)
                    .collect(Collectors.toList());

            result = new PageImpl<>(clubs, clubPage.getPageable(), clubPage.getTotalElements());
        }else{
            result = clubRepository.findByMember(member, pageable);
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
