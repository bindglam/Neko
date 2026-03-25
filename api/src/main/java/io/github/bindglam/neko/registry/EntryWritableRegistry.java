package io.github.bindglam.neko.registry;

import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public interface EntryWritableRegistry<T, E extends EntryWritableRegistry.RegistryEntry<T>> extends WritableRegistry<T> {
    @NotNull T register(@NotNull Key key, @NotNull Consumer<@NotNull E> entry);

    interface RegistryEntry<T> {
        @NotNull T toValue();
    }
}
