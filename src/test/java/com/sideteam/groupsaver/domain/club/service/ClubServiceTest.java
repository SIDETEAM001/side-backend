package com.sideteam.groupsaver.domain.club.service;

import com.sideteam.groupsaver.global.auth.userdetails.GetAuthUserUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class ClubServiceTest {

    ClubService clubService;

    @Test
    void createClub() {
        System.out.println(GetAuthUserUtils.getAuthUserId());
    }
}