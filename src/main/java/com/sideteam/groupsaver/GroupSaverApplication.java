package com.sideteam.groupsaver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class GroupSaverApplication {

    public static void main(String[] args) {
        SpringApplication.run(GroupSaverApplication.class, args);
    }

}
