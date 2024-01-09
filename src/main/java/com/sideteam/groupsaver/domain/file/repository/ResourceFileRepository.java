package com.sideteam.groupsaver.domain.file.repository;

import com.sideteam.groupsaver.domain.file.domain.ResourceFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceFileRepository extends JpaRepository<ResourceFile, Long> {
}
