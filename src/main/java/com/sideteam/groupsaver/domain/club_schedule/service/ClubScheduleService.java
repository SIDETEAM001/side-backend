package com.sideteam.groupsaver.domain.club_schedule.service;

import com.sideteam.groupsaver.domain.club.domain.Club;
import com.sideteam.groupsaver.domain.club.repository.ClubRepository;
import com.sideteam.groupsaver.domain.club_schedule.domain.ClubSchedule;
import com.sideteam.groupsaver.domain.club_schedule.dto.request.ClubScheduleRequest;
import com.sideteam.groupsaver.domain.club_schedule.dto.response.ClubScheduleResponse;
import com.sideteam.groupsaver.domain.club_schedule.repository.ClubScheduleRepository;
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
@Transactional
@Service
public class ClubScheduleService {

    private final ClubRepository clubRepository;
    private final ClubScheduleRepository clubScheduleRepository;


    @Transactional(readOnly = true)
    public ClubScheduleResponse getSchedule(Long clubScheduleId) {
        ClubSchedule clubSchedule = clubScheduleRepository.findOrThrowWithReadLock(clubScheduleId);
        return ClubScheduleResponse.from(clubSchedule);
    }

    @Transactional(readOnly = true)
    public Page<ClubScheduleResponse> getScheduleByClubId(Long clubId, Pageable pageable) {
        Page<ClubSchedule> clubSchedulePage = clubScheduleRepository.findAllByClubId(clubId, pageable);

        List<ClubScheduleResponse> clubScheduleResponses = clubSchedulePage.getContent().stream()
                .map(ClubScheduleResponse::from)
                .toList();
        return new PageImpl<>(clubScheduleResponses, clubSchedulePage.getPageable(), clubSchedulePage.getTotalElements());
    }

    public ClubScheduleResponse createSchedule(Long clubId, ClubScheduleRequest clubScheduleRequest) {
        Club club = clubRepository.getReferenceById(clubId);
        // TODO 해당 모임에 일정을 추가하는 권한 있는지 확인 로직 추가 필요
        ClubSchedule clubSchedule = clubScheduleRepository.save(ClubSchedule.of(club, clubScheduleRequest));
        return ClubScheduleResponse.from(clubSchedule);
    }

    public ClubScheduleResponse updateSchedule(Long clubScheduleId, ClubScheduleRequest clubScheduleRequest) {

        ClubSchedule clubSchedule = clubScheduleRepository.getReferenceById(clubScheduleId);
        clubSchedule.update(clubScheduleRequest);
        return ClubScheduleResponse.from(clubSchedule);
    }

    public void deleteSchedule(Long clubScheduleId) {
        // TODO 삭제 권한 체크
        clubScheduleRepository.deleteById(clubScheduleId);
    }

}
