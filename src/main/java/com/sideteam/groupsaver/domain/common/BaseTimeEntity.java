package com.sideteam.groupsaver.domain.common;

import com.sideteam.groupsaver.global.util.ProjectTimeFormat;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@ToString
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

    @CreatedDate
    private Instant createAt;

    @LastModifiedDate
    private Instant updateAt;

    public LocalDateTime getCreatedAtLocalDateTime() {
        return LocalDateTime.ofInstant(createAt, ProjectTimeFormat.SERVER_ZONE_ID);
    }

}
