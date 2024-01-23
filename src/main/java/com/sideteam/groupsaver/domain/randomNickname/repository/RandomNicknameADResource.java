package com.sideteam.groupsaver.domain.randomNickname.repository;

import com.sideteam.groupsaver.domain.randomNickname.domain.NicknameAD;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RandomNicknameADResource extends JpaRepository<NicknameAD, Long> {
    @Query("SELECT n.ad FROM nickname_ad n WHERE n.id = :id")
    String findAdById(long id);
}
