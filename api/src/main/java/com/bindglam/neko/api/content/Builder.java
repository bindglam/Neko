package com.bindglam.neko.api.content;

import org.jetbrains.annotations.NotNull;

public interface Builder<T> {
    @NotNull T build();
}
