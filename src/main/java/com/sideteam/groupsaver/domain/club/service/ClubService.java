package com.sideteam.groupsaver.domain.club.service;

import com.sideteam.groupsaver.domain.club.controller.ClubController;
import com.sideteam.groupsaver.domain.club.domain.Club;
import com.sideteam.groupsaver.domain.club.domain.ClubMember;
import com.sideteam.groupsaver.domain.club.domain.ClubMemberRole;
import com.sideteam.groupsaver.domain.club.dto.ClubCreateDto;
import com.sideteam.groupsaver.domain.club.repository.ClubMemberRepository;
import com.sideteam.groupsaver.domain.club.repository.ClubRepository;
import com.sideteam.groupsaver.domain.defaultImage.repository.DefaultMainImageRepository;
import com.sideteam.groupsaver.global.auth.userdetails.GetAuthUserUtils;
import com.sideteam.groupsaver.global.exception.BusinessException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import static com.sideteam.groupsaver.global.exception.clubA.ClubErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional
public class ClubService {
    private final ClubRepository repository;
    private final DefaultMainImageRepository defaultMainImageRepository;
    private final ClubMemberRepository clubMemberRepository;

    public Long createClub(ClubCreateDto dto) {
        Club entity = Club.of(dto.getName(), dto.getMemberNumMax(), dto.getType(), dto.getDescription(),dto.getCategoryType(),dto.getMainImage(), toLocalDateTime(dto.getStartClub()), dto.getActivityType());
        if (entity.getMainImage() == null) {
            entity.updateImage(randomDefaultMainImage());
        }
        repository.save(entity);
        createClubMember(entity.getId(), ClubMemberRole.LEADER);
        return entity.getId();
    }

    private String randomDefaultMainImage() {
        long randomId = new Random().nextLong(11) + 1;
        return defaultMainImageRepository.findById(randomId).orElseThrow(IllegalArgumentException::new).getUrl();
    }

    private LocalDateTime toLocalDateTime(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(dateStr, formatter);
    }

    private void createClubMember(long clubId, ClubMemberRole role) {
        long memberId = Long.parseLong(GetAuthUserUtils.getAuthUserId());
//        if (clubMemberRepository.existsByMemberId(memberId)) {
//            throw new BusinessException(CLUB_MEMBER_ALREADY_EXIST, "멤버 아이디 : " + memberId);
//        }
//        ClubMember entity = ClubMember.of(clubId, memberId, role);
//        clubMemberRepository.save(entity);
    }

    public void joinTheClub(long clubId) {
        if (!repository.findStatusByClubId(clubId)) {
            throw new BusinessException(CLUB_NOT_FOUND, "종결된 모임 아이디 : " + clubId);
        }
        Club entity = repository.findById(clubId).orElseThrow(() -> new BusinessException(CLUB_NOT_FOUND,
                "존재하지 않는 모임 아이디 : " + clubId));
        if (entity.getMemberCurrentNum() == entity.getMemberNumMax()) {
            throw new BusinessException(CLUB_MEMBER_IS_FULL, "모임 아이디 : " + String.valueOf(clubId));
        }
        createClubMember(clubId, ClubMemberRole.MEMBER);
    }

    public void updateDescription(long clubId, ClubController.RequestUpdateDescription dto) {

        checkTheReader(clubId);
        Club club = repository.findById(clubId).orElseThrow(() -> new BusinessException(CLUB_NOT_FOUND, "잘못된 모임 아이디 : " + String.valueOf(clubId) ));
        if (!club.isStatus()) {
            throw new BusinessException(CLUB_NOT_FOUND, "활동이 끝난 모임 아이디 : " + String.valueOf(clubId));
        }
        club.updateDescription(dto.description());
    }

    public void deleteClub(long clubId) {
        checkTheReader(clubId);
        repository.updateIsStatusByClubId(clubId);
    }

    public void checkTheReader(long clubId) {
        long memberId = Long.parseLong(GetAuthUserUtils.getAuthUserId());
//        if (clubMemberRepository.findRoleByMemberIdAndClubId(memberId, clubId) == ClubMemberRole.MEMBER) {
//            throw new BusinessException(MEMBER_DO_NOT_HAVE_PERMISSION, "멤버 아이디 : " + memberId);
//        }
    }
}