package com.bindglam.neko.api.utils;

import org.jetbrains.annotations.Unmodifiable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public final class CollectionUtils {
    private CollectionUtils() {
    }

    public static <T> @Unmodifiable List<T> copyAndAdd(List<T> list, T value) {
        List<T> copy = new ArrayList<>(list);
        copy.add(value);
        return copy;
    }

    public static <T> @Unmodifiable List<T> copyAndAddAll(List<T> list, Collection<T> values) {
        List<T> copy = new ArrayList<>(list);
        copy.addAll(values);
        return copy;
    }

    @SafeVarargs
    public static <T> @Unmodifiable List<T> copyAndAddAll(List<T> list, T... values) {
        List<T> copy = new ArrayList<>(list);
        copy.addAll(Arrays.stream(values).toList());
        return copy;
    }
}
