package com.bindglam.neko.api.utils;

import org.jetbrains.annotations.NotNull;

public interface Builder<T> {
    @NotNull T build();
}
