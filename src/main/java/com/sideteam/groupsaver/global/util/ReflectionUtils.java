package com.sideteam.groupsaver.global.util;

import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class ReflectionUtils {

    private static final String PACKAGE_NAME = "com.sideteam.groupsaver";

    @SuppressWarnings("unchecked")
    public static List<Class<? extends Enum<?>>> findEnumsByMethodAnnotation(Class<? extends Annotation> annotation) {
        Reflections reflections = new Reflections(PACKAGE_NAME, new SubTypesScanner(true), new MethodAnnotationsScanner());

        Set<Method> methodsWithAnnotation = reflections.getMethodsAnnotatedWith(annotation);

        return methodsWithAnnotation.stream()
                .map(Method::getDeclaringClass)
                .filter(Class::isEnum)
                .map(cls -> (Class<? extends Enum<?>>) cls)
                .distinct()
                .collect(Collectors.toList());
    }

}
