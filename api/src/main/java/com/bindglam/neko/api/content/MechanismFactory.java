package com.bindglam.neko.api.content;

import org.jetbrains.annotations.NotNull;

public interface MechanismFactory<T> {
    @NotNull Mechanism<T> create(@NotNull T content);

    @NotNull Class<T> type();
}
