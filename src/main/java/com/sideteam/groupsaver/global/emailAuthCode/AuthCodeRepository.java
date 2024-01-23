package com.sideteam.groupsaver.global.emailAuthCode;

import com.sideteam.groupsaver.global.auth.refresh_token.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface AuthCodeRepository extends CrudRepository<AuthCode, String> {
}
