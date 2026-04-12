package io.github.bindglam.neko.content.feature;

import io.github.bindglam.neko.content.Content;
import io.github.bindglam.neko.event.EventBus;
import org.jetbrains.annotations.NotNull;

public record FeatureBuilder(
        @NotNull FeatureFactory<?> factory,
        @NotNull FeatureArguments arguments
) {
    public @NotNull Feature build(@NotNull Content content, @NotNull EventBus eventBus) {
        return factory.create(new FeatureFactory.Context(content, arguments, eventBus));
    }
}
