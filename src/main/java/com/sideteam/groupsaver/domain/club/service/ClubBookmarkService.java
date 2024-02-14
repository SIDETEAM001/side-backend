package com.sideteam.groupsaver.domain.club.service;

import com.sideteam.groupsaver.domain.club.domain.Club;
import com.sideteam.groupsaver.domain.join.domain.ClubBookmark;
import com.sideteam.groupsaver.domain.join.repository.ClubBookmarkRepository;
import com.sideteam.groupsaver.domain.member.domain.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ClubBookmarkService {

    private final ClubBookmarkRepository clubBookmarkRepository;
    private final EntityManager entityManager;

    @PreAuthorize("@authorityChecker.hasAuthority(#memberId)")
    public void bookmarkClub(Long memberId, Long clubId) {
        Member member = entityManager.getReference(Member.class, memberId);
        Club club = entityManager.getReference(Club.class, clubId);

        clubBookmarkRepository.save(ClubBookmark.of(member, club));
    }

    @PreAuthorize("@authorityChecker.hasAuthority(#memberId)")
    public void deleteClubBookmark(Long memberId, Long clubId) {
        clubBookmarkRepository.deleteByMemberIdAndClubId(memberId, clubId);
    }

}
