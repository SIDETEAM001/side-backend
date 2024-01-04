package com.sideteam.groupsaver.global.resolver.member_info;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>memberId 파라미터를 찾고, 없으면 토큰 내 ID를 가져오는 에너테이션<br></p>
 * request 파라미터에서 ?memberId=1 로 값을 가져오거나,
 * 없다면, 토큰에서 id를 추출하는 방식으로도 값을 찾음
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface MemberIdParam {
}
