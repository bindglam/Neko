package io.github.bindglam.neko.registry;

import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;

public interface DirectWritableRegistry<T> extends WritableRegistry<T> {
    @NotNull T register(@NotNull Key key, @NotNull T value);
}
