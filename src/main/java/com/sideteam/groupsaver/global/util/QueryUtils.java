package com.sideteam.groupsaver.global.util;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.*;
import com.querydsl.core.types.dsl.*;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.LongSupplier;
import java.util.function.Supplier;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class QueryUtils {

    public static <T extends Enum<T>> BooleanExpression eq(@Nonnull EnumPath<T> path, @Nullable T input) {
        return input == null ? null : path.eq(input);
    }

    public static BooleanExpression eq(@Nonnull StringPath path, @Nullable String input) {
        return StringUtils.isBlank(input) ? null : path.eq(input);
    }

    public static BooleanExpression contains(@Nonnull StringPath path, @Nullable String input) {
        return StringUtils.isBlank(input) ? null : path.contains(input);
    }


    public static <T extends Comparable<?>> BooleanBuilder nullSafeBuilderEq(@Nonnull ComparablePath<T> path, @Nullable T input) {
        return nullSafeBuilder(() -> path.eq(input));
    }

    public static BooleanBuilder nullSafeBuilderEq(StringPath path, String input) {
        return nullSafeBuilder(() -> path.eq(input));
    }

    public static BooleanBuilder nullSafeBuilder(Supplier<BooleanExpression> f) {
        try {
            return new BooleanBuilder(f.get());
        } catch (IllegalArgumentException e) {
            return new BooleanBuilder();
        }
    }

    public static OrderSpecifier<?>[] getOrderSpecifiers(Pageable pageable, Path<?> path) {
        return QueryUtils.getOrderSpecifiers(pageable.getSort(), path).toArray(OrderSpecifier[]::new);
    }


    private static <T extends Comparable<T>> List<OrderSpecifier<T>> getOrderSpecifiers(Sort sort, Path<?> parent) {
        return sort.stream()
                .map(order -> createOrderSpecifier(order, parent, (Class<? extends T>) parent.getClass()))
                .toList();
    }

    private static <T extends Comparable<T>> OrderSpecifier<T> createOrderSpecifier(
            Sort.Order order, Path<?> parent, Class<? extends T> type) {

        Order direction = order.isAscending() ? Order.ASC : Order.DESC;
        ComparablePath<T> path = Expressions.comparablePath(type, parent, order.getProperty());
        return new OrderSpecifier<>(direction, path);
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
