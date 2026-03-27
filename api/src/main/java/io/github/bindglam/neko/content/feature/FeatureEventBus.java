package io.github.bindglam.neko.content.feature;

import io.github.bindglam.neko.content.feature.event.FeatureEvent;
import io.github.bindglam.neko.content.feature.event.FeatureEventSubscriber;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public interface FeatureEventBus {
    <T extends FeatureEvent> void subscribe(@NotNull Class<T> clazz, @NotNull FeatureEventSubscriber<T> subscriber);

    void call(@NotNull FeatureEvent event);
}
