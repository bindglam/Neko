package com.bindglam.neko.api.config;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public interface Configurable<T, C> {
    @Nullable T load(@Nullable C config);

    static <T, C> @Nullable List<T> parseAsList(@Nullable List<C> list, @NotNull Configurable<T, C> parser) {
        if(list == null) return null;

        return list.stream().collect(ArrayList::new, (result, config) -> result.add(parser.load(config)), ArrayList::addAll);
    }
}
