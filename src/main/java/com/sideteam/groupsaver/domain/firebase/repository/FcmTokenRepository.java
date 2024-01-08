package com.sideteam.groupsaver.domain.firebase.repository;

import com.sideteam.groupsaver.domain.firebase.domain.FcmToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

public interface FcmTokenRepository extends JpaRepository<FcmToken, Long> {
    boolean existsByEmail(String email);

    FcmToken findByEmail(String email);
}
