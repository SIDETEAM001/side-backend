package com.sideteam.groupsaver.domain.randomNickname.repository;

import com.sideteam.groupsaver.domain.randomNickname.domain.NicknameNoun;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RandomNicknameNounResource extends JpaRepository<NicknameNoun, Long> {
    @Query("SELECT n.noun FROM NicknameNoun n WHERE n.id = :id")
    String findNounById(long id);
}
