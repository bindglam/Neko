package io.github.bindglam.neko.registry;

import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;

public final class DirectScalableRegistry<T> extends ScalableRegistry<T> implements DirectWritableRegistry<T> {

    @Override
    public @NotNull T register(@NotNull Key key, @NotNull T value) {
        if (isLocked) {
            throw new IllegalStateException("The registry is locked");
        }
        if (map.containsKey(key.asString())) {
            throw new IllegalStateException("The registry already contains the key");
        }
        map.put(key.asString(), value);
        return value;
    }
}
