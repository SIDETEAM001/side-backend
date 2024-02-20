package com.sideteam.groupsaver.global.config;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.InstantSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.fasterxml.jackson.datatype.jsr310.ser.InstantSerializer.INSTANCE;
import static com.sideteam.groupsaver.global.util.ProjectTimeFormat.*;
import static java.time.format.DateTimeFormatter.ofPattern;

@Configuration
public class ObjectMapperConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.registerModule(customTimeModule());
        return objectMapper;
    }


    private Module customTimeModule() {
        SimpleModule dateTimeModule = new SimpleModule();

        dateTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(ofPattern(LOCAL_DATE_PATTERN)));
        dateTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(ofPattern(LOCAL_DATE_TIME_PATTERN)));
        dateTimeModule.addSerializer(Instant.class, new CustomInstantSerializer(INSTANCE,
                false, false, ofPattern(LOCAL_DATE_TIME_PATTERN).withZone(SERVER_ZONE_ID)));

        return dateTimeModule;
    }


    static class CustomInstantSerializer extends InstantSerializer {
        public CustomInstantSerializer(InstantSerializer base, boolean useTimestamp, boolean useNanoseconds, DateTimeFormatter formatter) {
            super(base, useTimestamp, useNanoseconds, formatter);
        }
    }

}
