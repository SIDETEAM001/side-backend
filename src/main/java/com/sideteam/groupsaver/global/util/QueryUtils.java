package com.sideteam.groupsaver.global.util;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.EnumPath;
import com.querydsl.core.types.dsl.StringPath;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.LongSupplier;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QueryUtils {

    public static <T extends Enum<T>> BooleanExpression eq(@Nonnull EnumPath<T> path, @Nullable T input) {
        return input == null ? null : path.eq(input);
    }

    public static BooleanExpression eq(@Nonnull StringPath path, @Nullable String input) {
        return StringUtils.isBlank(input) ? null : path.eq(input);
    }

    public static BooleanExpression contains(@Nonnull StringPath path, @Nullable String input) {
        return StringUtils.isBlank(input) ? null : path.contains(input);
    }

    public static <T> Page<T> createPage(
            @Nonnull List<T> contents,
            @Nonnull Pageable pageable,
            @Nonnull LongSupplier totalSupplier) {

        if (pageable.isUnpaged() || pageable.getOffset() == 0) {
            if (needsTotalCount(contents, pageable)) {
                return new PageImpl<>(contents, pageable, totalSupplier.getAsLong());
            }
            return new PageImpl<>(contents, pageable, contents.size());
        }

        if (!contents.isEmpty() && pageable.getPageSize() > contents.size()) {
            return new PageImpl<>(contents, pageable, pageable.getOffset() + contents.size());
        }

        return new PageImpl<>(contents, pageable, totalSupplier.getAsLong());
    }


    private static <T> boolean needsTotalCount(List<T> contents, Pageable pageable) {
        return !(pageable.isUnpaged() || pageable.getPageSize() > contents.size());
    }

}
