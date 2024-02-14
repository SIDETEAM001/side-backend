package com.sideteam.groupsaver.domain.member.service;

import com.sideteam.groupsaver.domain.club.domain.ClubMemberStatus;
import com.sideteam.groupsaver.domain.club.repository.ClubMemberRepository;
import com.sideteam.groupsaver.domain.join.repository.ClubBookmarkRepository;
import com.sideteam.groupsaver.domain.member.dto.response.MyBookmarkClubResponse;
import com.sideteam.groupsaver.domain.member.dto.response.MyClubResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberClubService {

    private final ClubBookmarkRepository clubBookmarkRepository;
    private final ClubMemberRepository clubMemberRepository;


    public Slice<MyClubResponse> getMyClubs(Long memberId, Pageable pageable) {
        return clubMemberRepository.findByMemberIdAndStatus(memberId, ClubMemberStatus.ACTIVITY, pageable)
                .map(MyClubResponse::of);
    }

    public Slice<MyBookmarkClubResponse> getMyBookmarkClubs(Long memberId, Pageable pageable) {
        return clubBookmarkRepository.findByMemberId(memberId, pageable)
                .map(MyBookmarkClubResponse::of);
    }

}
