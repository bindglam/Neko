package io.github.bindglam.neko.platform;

import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface PlatformPersistentDataContainer {
    <P, C> @Nullable C get(@NotNull Key key, @NotNull Type<P, C> type);

    <P, C> void set(@NotNull Key key, @NotNull Type<P, C> type, @NotNull C value);

    interface Type<P, C> {
        @NotNull Object unwrap();
    }
}
