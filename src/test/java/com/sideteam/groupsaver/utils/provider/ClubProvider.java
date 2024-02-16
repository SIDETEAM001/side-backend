package com.sideteam.groupsaver.utils.provider;

import com.sideteam.groupsaver.domain.category.domain.ClubCategory;
import com.sideteam.groupsaver.domain.category.domain.ClubCategoryMajor;
import com.sideteam.groupsaver.domain.club.domain.Club;
import com.sideteam.groupsaver.domain.club.domain.ClubActivityType;
import com.sideteam.groupsaver.domain.club.domain.ClubType;

import java.util.UUID;

public final class ClubProvider {

    public static Club createClub() {
        return Club.builder()
                .name("test-club-" + UUID.randomUUID())
                .description("test-description " + UUID.randomUUID())
                .type(createClubType())
                .category(createClubCategory())
                .categoryMajor(createClubCategoryMajor())
                .activityType(createClubActivityType())
                .memberMaxNumber(10_000)
                .build();
    }

    public static Club createClub(int maxMemberCount) {
        return Club.builder()
                .name("test-club-" + UUID.randomUUID())
                .description("test-description " + UUID.randomUUID())
                .type(createClubType())
                .category(createClubCategory())
                .categoryMajor(createClubCategoryMajor())
                .activityType(createClubActivityType())
                .memberMaxNumber(maxMemberCount)
                .build();
    }

    private static ClubType createClubType() {
        double randomValue = Math.random();
        if (randomValue < 1.0 / 3) {
            return ClubType.ONE;
        } else if (randomValue < 2.0 / 3) {
            return ClubType.SHORT;
        }
        return ClubType.LONG;
    }

    private static ClubCategory createClubCategory() {
        return ClubCategory.values()[(int) (Math.random() * ClubCategory.values().length)];
    }

    private static ClubCategoryMajor createClubCategoryMajor() {
        return ClubCategoryMajor.values()[(int) (Math.random() * ClubCategoryMajor.values().length)];
    }

    private static ClubActivityType createClubActivityType() {
        return Math.random() > 0.5 ? ClubActivityType.ONLINE : ClubActivityType.OFFLINE;
    }
}
