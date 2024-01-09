package com.sideteam.groupsaver.domain.hobby.service;

import com.sideteam.groupsaver.domain.defaultImage.repository.DefaultMainImageRepository;
import com.sideteam.groupsaver.domain.hobby.domain.Hobby;
import com.sideteam.groupsaver.domain.hobby.dto.HobbyCreateDto;
import com.sideteam.groupsaver.domain.hobby.repository.HobbyRepository;
import com.sideteam.groupsaver.global.exception.BusinessException;
import com.sideteam.groupsaver.global.exception.club.ClubErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Service
@Transactional
@RequiredArgsConstructor
public class HobbyService {
    private final HobbyRepository repository;
    private final DefaultMainImageRepository defaultMainImageRepository;

    public Hobby createHobby(HobbyCreateDto dto) {
        Hobby entity = Hobby.of(dto.getName(), dto.getMemberNumMax(), dto.getType(), dto.getDescription(), dto.getMainImage(), toLocalDateTime(dto.getStartClub()), dto.getActivityType(), dto.getLocation());
        if (entity.getMainImage() == null) {
            entity.updateImage(randomDefaultMainImage());
        }
        repository.save(entity);
        return entity;
    }

    public void updateDescription(long hobbyId, String description) {
        Hobby hobby = findTheHobby(hobbyId);
        hobby.updateDescription(description);
    }

    public void deleteHobby(long hobbyId) {
        repository.updateIsStatusByHobbyId(hobbyId);
    }

    public Hobby findTheHobby(long hobbyId) {
        return repository.findById(hobbyId).orElseThrow(() -> new BusinessException(ClubErrorCode.CLUB_NOT_FOUND, "잘못된 모임 아이디 : " + hobbyId));
    }

    public Hobby checkHobby(long hobbyId) {
        Hobby hobby = findTheHobby(hobbyId);
        if (hobby.getMemberCurrentNum() == hobby.getMemberNumMax()) {
            throw new BusinessException(ClubErrorCode.CLUB_MEMBER_IS_FULL, "클럼 정원이 마감되었습니다.");
        }
        return hobby;
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
