package com.sideteam.groupsaver.domain.club_schedule.service;

import com.sideteam.groupsaver.domain.club.domain.Club;
import com.sideteam.groupsaver.domain.club.repository.ClubRepository;
import com.sideteam.groupsaver.domain.club_schedule.domain.ClubSchedule;
import com.sideteam.groupsaver.domain.club_schedule.dto.ClubScheduleRequestDto;
import com.sideteam.groupsaver.domain.club_schedule.dto.ClubScheduleResponseDto;
import com.sideteam.groupsaver.domain.club_schedule.repository.ClubScheduleRepository;
import com.sideteam.groupsaver.global.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.sideteam.groupsaver.global.exception.club.ClubScheduleErrorCode.CLUB_SCHEDULE_NOT_FOUND;

@RequiredArgsConstructor
@Slf4j
@Transactional
@Service
public class ClubScheduleService {

    private final ClubRepository clubRepository;
    private final ClubScheduleRepository clubScheduleRepository;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Transactional(readOnly = true)
    public ClubScheduleResponseDto getSchedule(Long clubScheduleId) {
        ClubSchedule clubSchedule = findClubScheduleOrThrow(clubScheduleId);
        return ClubScheduleResponseDto.from(clubSchedule);
    }

    @Transactional(readOnly = true)
    public Page<ClubScheduleResponseDto> getScheduleByClubId(Long clubId, Pageable pageable) {
        Page<ClubSchedule> clubSchedulePage = clubScheduleRepository.findAllByClubId(clubId, pageable);

        List<ClubScheduleResponseDto> clubScheduleResponses = clubSchedulePage.getContent().stream()
                .map(ClubScheduleResponseDto::from)
                .toList();
        return new PageImpl<>(clubScheduleResponses, clubSchedulePage.getPageable(), clubSchedulePage.getTotalElements());
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

        clubSchedule.update(LocalDateTime.parse(clubScheduleRequestDto.meetAt(), formatter),
                clubScheduleRequestDto.title());

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
