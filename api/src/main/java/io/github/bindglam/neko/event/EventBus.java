package io.github.bindglam.neko.event;

import org.jetbrains.annotations.NotNull;

public interface EventBus {
    <T extends Event> void subscribe(@NotNull Class<T> clazz, @NotNull EventSubscriber<T> subscriber);

    void call(@NotNull Event event);
}
