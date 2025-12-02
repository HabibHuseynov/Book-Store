package com.example.catalog.util;

import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.Collection;

public final class SpecificationUtil {

    private SpecificationUtil() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    @NullMarked
    public static <T> Specification<T> like(String fieldName, String value) {
        if (!StringUtils.hasText(value)) {
            return Specification.unrestricted();
        }
        return (root, query, cb) -> {
            Path<String> path = getPath(root, fieldName);
            return cb.like(cb.lower(path), "%" + value.toLowerCase() + "%");
        };
    }

    @NullMarked
    public static <T> Specification<T> equal(String fieldName, Object value) {
        if (value == null) {
            return Specification.unrestricted();
        }
        return (root, query, cb) -> {
            Path<?> path = getPath(root, fieldName);
            return cb.equal(path, value);
        };
    }

    @NullMarked
    public static <T> Specification<T> in(String fieldName, Collection<?> values) {
        if (values == null || values.isEmpty()) {
            return Specification.unrestricted();
        }
        return (root, query, cb) -> {
            Path<?> path = getPath(root, fieldName);
            return path.in(values);
        };
    }

    @NullMarked
    public static <T, Y extends Comparable<? super Y>> Specification<T> range(String fieldName, Y from, Y to) {
        if (from == null && to == null) {
            return Specification.unrestricted();
        }
        return (root, query, cb) -> {
            Path<Y> path = getPath(root, fieldName);
            if (from != null && to != null) {
                return cb.between(path, from, to);
            } else if (from != null) {
                return cb.greaterThanOrEqualTo(path, from);
            } else {
                return cb.lessThanOrEqualTo(path, to);
            }
        };
    }

    @SuppressWarnings("unchecked")
    private static <X, Y> Path<Y> getPath(Root<X> root, String attributeName) {
        Path<?> path = root;
        if (attributeName.contains(".")) {
            for (String part : attributeName.split("\\.")) {
                path = path.get(part);
            }
        } else {
            path = root.get(attributeName);
        }
        return (Path<Y>) path;
    }
}