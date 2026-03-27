package io.github.bindglam.neko.content.feature;

import io.github.bindglam.neko.content.feature.event.FeatureEvent;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public interface FeatureEventBus {
    <T extends FeatureEvent> void subscribe(@NotNull Class<T> clazz, @NotNull Consumer<T> consumer);
}
