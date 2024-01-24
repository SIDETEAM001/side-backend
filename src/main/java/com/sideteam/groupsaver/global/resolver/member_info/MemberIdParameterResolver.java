package com.sideteam.groupsaver.global.resolver.member_info;

import com.sideteam.groupsaver.global.auth.userdetails.CustomUserDetails;
import com.sideteam.groupsaver.global.exception.auth.AuthErrorException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static com.sideteam.groupsaver.global.exception.auth.AuthErrorCode.FAILED_AUTHENTICATION;
import static io.micrometer.common.util.StringUtils.isBlank;

@Slf4j
@Component
public class MemberIdParameterResolver implements HandlerMethodArgumentResolver {

    private static final String MEMBER_ID_PARAMETER = "memberId";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(MemberIdParam.class) != null
                && parameter.getParameterType().equals(Long.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) {
        try {
            final HttpServletRequest httpServletRequest = webRequest.getNativeRequest(HttpServletRequest.class);

            final String memberId = httpServletRequest.getParameter(MEMBER_ID_PARAMETER);

            if (isBlank(memberId)) {
                return getMemberIdFromContext();
            }
            return Long.parseLong(memberId);

        } catch (NullPointerException | NumberFormatException ex) {
            throw new AuthErrorException(FAILED_AUTHENTICATION, "Member ID나 토큰이 필요합니다");
        }
    }


    private Long getMemberIdFromContext() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails userDetails) {
            return userDetails.getId();
        }

        throw new AuthErrorException(FAILED_AUTHENTICATION, "Member ID나 토큰이 필요합니다");
    }

}
