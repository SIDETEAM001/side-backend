package com.sideteam.groupsaver.domain.club.dto.response;

import java.util.List;
import java.util.Set;

import static java.util.Collections.emptyList;

public record ClubSearchHistoryResponse(
        List<String> words,
        Long memberId
) {
    public static ClubSearchHistoryResponse of(Set<String> words, Long memberId) {
        if (words == null || words.isEmpty()) {
            return new ClubSearchHistoryResponse(emptyList(), memberId);
        }
        return new ClubSearchHistoryResponse(words.stream().toList(), memberId);
    }
}
