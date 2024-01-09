package com.sideteam.groupsaver.domain.file.domain;

import com.sideteam.groupsaver.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.net.URL;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class ResourceFile extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String fileKey;

    @Column(unique = true, nullable = false)
    private URL path;

    @Builder
    protected ResourceFile(String fileKey, URL path) {
        this.fileKey = fileKey;
        this.path = path;
    }

}
