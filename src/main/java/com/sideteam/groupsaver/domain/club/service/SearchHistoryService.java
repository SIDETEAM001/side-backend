package com.sideteam.groupsaver.domain.club.service;

import com.sideteam.groupsaver.domain.club.dto.response.ClubSearchHistoryResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.Set;

@Service
public class SearchHistoryService {

    private static final String PREFIX_SEARCH_HISTORY = "SearchHistory-";

    private final ZSetOperations<String, String> zSetOperations;

    private final long maxHistorySaveCount;
    private final long historyDisplayCount;


    public SearchHistoryService(
            StringRedisTemplate redisTemplate,
            @Value("${app.search.history.max}") Long maxHistorySaveCount,
            @Value("${app.search.history.display}") Long historyDisplayCount) {
        this.zSetOperations = redisTemplate.opsForZSet();
        this.maxHistorySaveCount = maxHistorySaveCount;
        this.historyDisplayCount = historyDisplayCount;
    }


    @PreAuthorize("@authorityChecker.hasAuthority(#memberId)")
    public void saveSearchWord(String text, Long memberId) {
        if (!StringUtils.hasText(text)) {
            return;
        }
        zSetOperations.add(PREFIX_SEARCH_HISTORY + memberId, text, timestamp());
        zSetOperations.removeRange(
                PREFIX_SEARCH_HISTORY + memberId,
                -(maxHistorySaveCount + 1), -(maxHistorySaveCount));
    }

    @PreAuthorize("@authorityChecker.hasAuthority(#memberId)")
    public ClubSearchHistoryResponse getRecentSearchHistory(Long memberId) {
        Set<String> words = zSetOperations.reverseRange(
                PREFIX_SEARCH_HISTORY + memberId,
                0, historyDisplayCount - 1);
        return ClubSearchHistoryResponse.of(words, memberId);
    }


    private static long timestamp() {
        return Instant.now().toEpochMilli();
    }

}
