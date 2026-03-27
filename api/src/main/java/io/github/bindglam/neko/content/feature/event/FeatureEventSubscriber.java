package io.github.bindglam.neko.content.feature.event;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public interface FeatureEventSubscriber<T extends FeatureEvent> {
    void onCalled(@NotNull T event);

    @ApiStatus.Internal
    @SuppressWarnings("unchecked")
    default void onCalled(@NotNull Object event) {
        onCalled((T) event);
    }
}
