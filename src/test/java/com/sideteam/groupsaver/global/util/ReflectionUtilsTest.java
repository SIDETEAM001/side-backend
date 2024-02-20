package com.sideteam.groupsaver.global.util;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReflectionUtilsTest {

    @Test
    void test() {
        List<Class<? extends Enum<?>>> classes = ReflectionUtils.findEnumsByMethodAnnotation(TestAnnotation.class);
        assertTrue(classes.contains(TestEnumWithAnnotation.class));
        assertFalse(classes.contains(TestEnumNoAnnotation.class));
    }


    enum TestEnumWithAnnotation {
        TEST1, TEST2,
        ;

        @TestAnnotation
        public static TestEnumWithAnnotation fromString(String value) {
            return TestEnumWithAnnotation.valueOf(value.trim());
        }

    }

    enum TestEnumNoAnnotation {
        TEST1, TEST2,
        ;

        public TestEnumWithAnnotation fromString(String value) {
            return TestEnumWithAnnotation.valueOf(value.trim());
        }
    }


    @interface TestAnnotation {
    }

}
