package com.sideteam.groupsaver.global.resolver.member_info;

import io.swagger.v3.oas.annotations.media.Schema;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>memberId 파라미터를 찾고, 없으면 토큰 내 ID를 가져오는 에너테이션<br></p>
 * request 파라미터에서 ?memberId=1 로 값을 가져오거나,
 * 없다면, 토큰에서 id를 추출하는 방식으로도 값을 찾음
 */
@Schema(description = "요청 URI 파라미터로 지정한 멤버 ID 또는 Access 토큰에 해당하는 ID. <br/>" +
        "생략 가능하나, 원하는 사용자 ID 지정 가능 (권한 부족시 에러 응답 발생)",
        example = "생략, 또는 192",
        type = "number",
        nullable = true)
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface MemberIdParam {
}
