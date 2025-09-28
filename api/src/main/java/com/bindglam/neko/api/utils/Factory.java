package com.bindglam.neko.api.utils;

import org.jetbrains.annotations.NotNull;

public interface Factory<T, O> {
    @NotNull T create(O object);
}
