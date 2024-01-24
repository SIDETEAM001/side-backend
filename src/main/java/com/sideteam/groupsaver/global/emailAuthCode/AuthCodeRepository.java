package com.sideteam.groupsaver.global.emailAuthCode;

import org.springframework.data.repository.CrudRepository;

public interface AuthCodeRepository extends CrudRepository<AuthCode, String> {
}
