package io.github.bindglam.neko.event;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public interface EventSubscriber<T extends Event> {
    void onCalled(@NotNull T event);

    @ApiStatus.Internal
    @SuppressWarnings("unchecked")
    default void onCalled(@NotNull Object event) {
        onCalled((T) event);
    }
}
