package com.sideteam.groupsaver.domain.club.service;

import com.sideteam.groupsaver.domain.category.domain.ClubCategory;
import com.sideteam.groupsaver.domain.category.domain.DevelopMajor;
import com.sideteam.groupsaver.domain.category.repository.ClubCategoryRepository;
import com.sideteam.groupsaver.domain.club.controller.ClubController;
import com.sideteam.groupsaver.domain.club.domain.Club;
import com.sideteam.groupsaver.domain.club.domain.ClubMember;
import com.sideteam.groupsaver.domain.club.domain.ClubMemberRole;
import com.sideteam.groupsaver.domain.club.dto.ClubCreateDto;
import com.sideteam.groupsaver.domain.club.repository.ClubMemberRepository;
import com.sideteam.groupsaver.domain.club.repository.ClubRepository;
import com.sideteam.groupsaver.domain.defaultImage.repository.DefaultMainImageRepository;
import com.sideteam.groupsaver.domain.member.domain.Member;
import com.sideteam.groupsaver.domain.member.repository.MemberRepository;
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

    public Club createClub(ClubCreateDto dto) {
        Club entity = Club.of(dto.getName(), dto.getMemberNumMax(), dto.getType(), dto.getDescription(), dto.getMainImage(), toLocalDateTime(dto.getStartClub()), dto.getActivityType(), dto.getLocation());
        if (entity.getMainImage() == null) {
            entity.updateImage(randomDefaultMainImage());
        }
        repository.save(entity);
        return entity;
    }


    public void updateDescription(long clubId, String description) {
        Club club = findTheClub(clubId);
        club.updateDescription(description);
    }

    public void deleteClub(long clubId) {
        repository.updateIsStatusByClubId(clubId);
    }

    public Club findTheClub(long clubId) {
        return repository.findById(clubId).orElseThrow(() -> new BusinessException(CLUB_NOT_FOUND, "잘못된 모임 아이디 : " + String.valueOf(clubId)));
    }

    public Club checkClub(long clubId) {
        Club club = findTheClub(clubId);
        if (club.getMemberCurrentNum() == club.getMemberNumMax()) {
            throw new BusinessException(CLUB_MEMBER_IS_FULL, "클럼 정원이 마감되었습니다.");
        }
        return club;
    }

    private String randomDefaultMainImage() {
        long randomId = new Random().nextLong(11) + 1;
        return defaultMainImageRepository.findById(randomId).orElseThrow(IllegalArgumentException::new).getUrl();
    }

    private LocalDateTime toLocalDateTime(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(dateStr, formatter);
    }
}