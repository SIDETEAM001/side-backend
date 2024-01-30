package com.sideteam.groupsaver.domain.firebase.repository;

import com.sideteam.groupsaver.domain.firebase.domain.FcmToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FcmTokenRepository extends JpaRepository<FcmToken, Long> {
    boolean existsByToken(String token);

    @Query("SELECT f.token FROM FcmToken f WHERE f.email = :email")
    List<String> findAllTokenByEmail(String email);

    void deleteByToken(String token);
}
