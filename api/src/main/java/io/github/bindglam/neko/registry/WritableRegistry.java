package io.github.bindglam.neko.registry;

import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public interface WritableRegistry<T, E extends WritableRegistry.RegistryEntry<T>> extends Registry<T> {
    void lock();

    void unlock();

    @NotNull T register(@NotNull Key key, @NotNull Consumer<@NotNull E> entry);

    void merge(@NotNull Registry<T> registry);

    interface RegistryEntry<T> {
        @NotNull T toValue();
    }
}
