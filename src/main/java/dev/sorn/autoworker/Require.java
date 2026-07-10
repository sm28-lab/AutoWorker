package dev.sorn.autoworker;

import java.util.Map;
import java.util.Objects;

public final class Require {

    private Require() {}

    public static <T> T requireNonNull(String name, T value) {
        return Objects.requireNonNull(value, "'%s' is required".formatted(name));
    }

    public static <K, V> Map<K, V> requireNonNull(String name, Map<K, V> map) {
        return Objects.requireNonNull(map, "'%s' is required".formatted(name));
    }

    public static String requireNonEmpty(String name, String value) {
        if (requireNonNull(name, value).isEmpty()) {
            throw new IllegalArgumentException("'%s' cannot be empty".formatted(name));
        }
        return value;
    }

    public static <K, V> Map<K, V> requireNonEmpty(String name, Map<K, V> map) {
        if (requireNonNull(name, map).isEmpty()) {
            throw new IllegalArgumentException("'%s' cannot be empty".formatted(name));
        }
        return map;
    }

}
