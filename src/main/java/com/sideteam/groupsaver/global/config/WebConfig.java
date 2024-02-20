package com.sideteam.groupsaver.global.config;

import com.sideteam.groupsaver.global.util.ReflectionUtils;
import com.sideteam.groupsaver.global.util.converter.param.DynamicEnumConverter;
import com.sideteam.groupsaver.global.util.converter.param.ParamCreator;
import jakarta.annotation.Nonnull;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(@Nonnull FormatterRegistry registry) {
        ReflectionUtils.findEnumsByMethodAnnotation(ParamCreator.class)
                .forEach(type -> registry.addConverter(String.class, type, new DynamicEnumConverter<>(type)));
    }

}
