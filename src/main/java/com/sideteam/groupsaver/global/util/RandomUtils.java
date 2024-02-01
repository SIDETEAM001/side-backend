package com.sideteam.groupsaver.global.util;

import lombok.Getter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Getter
@Component
public class RandomUtils {

    private int randomValue = 50_000_000;

    @Scheduled(cron = "0 0 0 * * ?", zone = ProjectTimeFormat.SERVER_TIMEZONE)
    protected void update() {
        randomValue = (int) (Math.random() * 100_000_000);
    }

}
