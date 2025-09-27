package com.bindglam.neko.api.registry;

import org.jetbrains.annotations.NotNull;

public interface Holder<T> {
    @NotNull T value();

    static <T> Holder<T> create(T value) {
        return new Impl<>(value);
    }

    class Impl<T> implements Holder<T> {
        private final T value;

        private Impl(T value) {
            this.value = value;
        }

        @Override
        public @NotNull T value() {
            return value;
        }
    }
}
