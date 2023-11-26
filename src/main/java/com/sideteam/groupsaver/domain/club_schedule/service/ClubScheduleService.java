package com.sideteam.groupsaver.domain.club_schedule.service;

import com.sideteam.groupsaver.domain.club_schedule.domain.ClubSchedule;
import com.sideteam.groupsaver.domain.club_schedule.dto.ClubScheduleRequestDto;
import com.sideteam.groupsaver.domain.club_schedule.dto.ClubScheduleResponseDto;
import com.sideteam.groupsaver.domain.club_schedule.repository.ClubScheduleRepository;
import com.sideteam.groupsaver.global.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.util.List;

import static com.sideteam.groupsaver.global.exception.club.ClubScheduleErrorCode.CLUB_SCHEDULE_NOT_FOUND;

@RequiredArgsConstructor
@Slf4j
@Transactional
@Service
public class ClubScheduleService {

    private final ClubRepository clubRepository;
    private final ClubScheduleRepository clubScheduleRepository;

    @Transactional(readOnly = true)
    public ClubScheduleResponseDto getSchedule(Long clubScheduleId) {
        ClubSchedule clubSchedule = findClubScheduleOrThrow(clubScheduleId);
        return ClubScheduleResponseDto.from(clubSchedule);
    }

    @Transactional(readOnly = true)
    public List<ClubScheduleResponseDto> getScheduleByClubId(Long clubId, Pageable pageable) {
        return clubScheduleRepository.findAllByClubId(clubId, pageable)
                .stream()
                .map(ClubScheduleResponseDto::from)
                .toList();
    }

    public ClubScheduleResponseDto createSchedule(Long clubId, ClubScheduleRequestDto clubScheduleRequestDto) {
        Club club = findClubOrThrow(clubId);
        // TODO 해당 모임에 일정을 추가하는 권한 있는지 확인 로직 추가 필요
        ClubSchedule clubSchedule = clubScheduleRepository.save(ClubSchedule.of(club, clubScheduleRequestDto));
        return ClubScheduleResponseDto.from(clubSchedule);
    }

    public ClubScheduleResponseDto updateSchedule(
            Long clubScheduleId,
            ClubScheduleRequestDto clubScheduleRequestDto) {

        ClubSchedule clubSchedule = findClubScheduleOrThrow(clubScheduleId);
        clubSchedule.update(clubScheduleRequestDto.meetAt(), clubScheduleRequestDto.title());

        return ClubScheduleResponseDto.from(clubSchedule);
    }

    public void deleteSchedule(Long clubScheduleId) {
        // TODO 삭제 권한 체크
        clubScheduleRepository.deleteById(clubScheduleId);
    }

    private Club findClubOrThrow(Long clubId) {
        return clubRepository.getReferenceById(clubId);
    }

    private ClubSchedule findClubScheduleOrThrow(Long clubScheduleId) {
        return clubScheduleRepository.findById(clubScheduleId)
                .orElseThrow(() -> new BusinessException(CLUB_SCHEDULE_NOT_FOUND, clubScheduleId.toString()));
    }


}
