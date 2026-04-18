package io.github.bindglam.neko.utils;

import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public final class Inlines {
    private Inlines() {
    }

    public static <T> @NotNull T apply(@NotNull T t, @NotNull Consumer<@NotNull T> applier) {
        applier.accept(t);
        return t;
    }
}
