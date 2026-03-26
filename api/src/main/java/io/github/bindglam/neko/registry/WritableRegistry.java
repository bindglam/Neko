package io.github.bindglam.neko.registry;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public interface WritableRegistry<T> extends Registry<T> {
    void lock();

    void unlock();

    void merge(@NotNull Registry<T> registry);

    @ApiStatus.Internal
    void clear();
}
