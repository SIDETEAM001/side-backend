package com.sideteam.groupsaver.domain.common;

import com.sideteam.groupsaver.domain.member.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity extends BaseTimeEntity {

    @CreatedBy
    @JoinColumn(updatable = false, name = "creator_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member creator;

    @LastModifiedBy
    @JoinColumn(name = "updater_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member updater;

}
