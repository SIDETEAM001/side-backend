package com.sideteam.groupsaver.global.resolver;

import com.sideteam.groupsaver.global.resolver.access_token.AccessTokenInfoResolver;
import com.sideteam.groupsaver.global.resolver.member_info.MemberIdParameterResolver;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Configuration
public class ArgumentResolverConfig implements WebMvcConfigurer {

    private final AccessTokenInfoResolver accessTokenInfoResolver;
    private final MemberIdParameterResolver memberIdParameterResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.addAll(List.of(
                accessTokenInfoResolver, memberIdParameterResolver
        ));
    }
}
