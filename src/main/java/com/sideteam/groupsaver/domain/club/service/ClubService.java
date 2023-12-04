package com.sideteam.groupsaver.domain.club.service;

import com.sideteam.groupsaver.domain.club.domain.Club;
import com.sideteam.groupsaver.domain.club.domain.ClubActivityType;
import com.sideteam.groupsaver.domain.club.domain.ClubMajor;
import com.sideteam.groupsaver.domain.club.domain.ClubType;
import com.sideteam.groupsaver.domain.club.dto.ClubCreateDto;
import com.sideteam.groupsaver.domain.club.repository.ClubRepository;
import com.sideteam.groupsaver.domain.defaultImage.repository.DefaultMainImageRepository;
import com.sideteam.groupsaver.global.auth.userdetails.GetAuthUserUtils;
import com.sideteam.groupsaver.global.auth.userdetails.UserDetailsServiceImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional
public class ClubService {
    private final ClubRepository repository;
    private final DefaultMainImageRepository defaultMainImageRepository;

    public Long createClub(ClubCreateDto dto) {
        long memberId = Long.parseLong(GetAuthUserUtils.getAuthUserId());
        Club entity = Club.of(dto.getName(), dto.getMemberNumMax(), dto.getType(), dto.getDescription(),dto.getCategory(),dto.getMainImage(),dto.getStartClub(),dto.getActivityType(), memberId);
        if (entity.getMainImage().isEmpty()) {
            entity.updateImage(randomDefaultMainImage());
        }
        repository.save(entity);
        return entity.getId();
    }

    public String randomDefaultMainImage() {
        long randomId = new Random().nextLong(11) + 1;
        return defaultMainImageRepository.findById(randomId).orElseThrow(IllegalArgumentException::new).getUrl();
    }
}
