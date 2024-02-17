package com.sideteam.groupsaver.global.util.converter.param;

import org.springframework.core.convert.converter.Converter;

import java.lang.reflect.Method;
import java.util.Arrays;

public class DynamicEnumConverter<T extends Enum<?>> implements Converter<String, T> {

    private final Class<T> enumType;

    @SuppressWarnings("unchecked")
    public <K extends Enum<?>> DynamicEnumConverter(Class<K> enumType) {
        this.enumType = (Class<T>) enumType;
    }


    @Override
    public T convert(String source) {
        return convertFromString(enumType, source);
    }

    private static <T extends Enum<?>> T convertFromString(final Class<T> enumType, final String source) {
        final Method convertMethod = Arrays.stream(enumType.getMethods())
                .filter(method -> method.isAnnotationPresent(ParamCreator.class))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No @ParamCreator annotated method found for " + enumType.getName()));

        try {
            if (canConvert(enumType, convertMethod)) {
                @SuppressWarnings("unchecked")
                T result = (T) convertMethod.invoke(null, source);
                return result;
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to invoke ParamCreator method, ", e);
        }

        throw new AssertionError("Cannot be reached, " + enumType.getName());
    }

    private static boolean canConvert(Class<?> enumType, Method method) {
        return method.getReturnType().equals(enumType)
                && (method.getParameterTypes().length == 1)
                && method.getParameterTypes()[0].equals(String.class);
    }

}
