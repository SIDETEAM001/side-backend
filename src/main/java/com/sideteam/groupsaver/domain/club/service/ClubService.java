package com.sideteam.groupsaver.domain.club.service;

import com.sideteam.groupsaver.domain.club.domain.Club;
import com.sideteam.groupsaver.domain.club.dto.request.ClubRequest;
import com.sideteam.groupsaver.domain.club.dto.response.ClubInfoResponse;
import com.sideteam.groupsaver.domain.club.repository.ClubRepository;
import com.sideteam.groupsaver.domain.location.domain.Location;
import com.sideteam.groupsaver.domain.location.repository.LocationRepository;
import com.sideteam.groupsaver.domain.member.domain.Member;
import com.sideteam.groupsaver.domain.notification.service.NotificationService;
import com.sideteam.groupsaver.global.auth.userdetails.GetAuthUserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional
public class ClubService {
    private final ClubRepository clubRepository;
    private final LocationRepository locationRepository;
    private final NotificationService notificationService;

    public ClubInfoResponse createClub(ClubRequest clubRequest) {
        if (clubRequest.locationInfo() != null && clubRequest.locationInfo().isValidLocation()) {
            Location location = locationRepository.getReferenceById(clubRequest.locationInfo().id());
            Club club = clubRepository.save(Club.of(clubRequest, location));
            return ClubInfoResponse.of(club);
        }

        Club club = clubRepository.save(Club.of(clubRequest));
        notificationService.createNewClub(club, GetAuthUserUtils.getAuthUserId());
        return ClubInfoResponse.of(club);
    }


    @Transactional(readOnly = true)
    public ClubInfoResponse getClubInformation(Long clubId) {
        return ClubInfoResponse.of(clubRepository.findByIdOrThrow(clubId));
    }

    @PreAuthorize("@authorityChecker.hasAuthority(#memberId, @clubMemberRepository.isLeader(#clubId, #memberId))")
    public ClubInfoResponse updateClub(Long clubId, ClubRequest clubRequest, Long memberId) {
        Club club = clubRepository.getReferenceById(clubId);
        club.update(clubRequest);

        if (clubRequest.locationInfo() != null && clubRequest.locationInfo().isValidLocation()) {
            Location location = locationRepository.getReferenceById(clubRequest.locationInfo().id());
            club.updateLocation(location);
        }

        return ClubInfoResponse.of(club);
    }

    @PreAuthorize("@authorityChecker.hasAuthority(#memberId, @clubMemberRepository.isLeader(#clubId, #memberId))")
    public void deactivateClub(Long clubId, Long memberId) {
        clubRepository.deactivateClub(clubId);
    }

}
